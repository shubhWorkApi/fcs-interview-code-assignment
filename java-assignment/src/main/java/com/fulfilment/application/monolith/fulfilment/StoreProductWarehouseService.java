package com.fulfilment.application.monolith.fulfilment;

import com.fulfilment.application.monolith.products.Product;
import com.fulfilment.application.monolith.products.ProductRepository;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.warehouses.adapters.database.DbWarehouse;
import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StoreProductWarehouseService {

    private final StoreProductWarehouseRepository repository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public StoreProductWarehouseService(
            StoreProductWarehouseRepository repository,
            ProductRepository productRepository,
            WarehouseRepository warehouseRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public void associate(Long storeId, Long productId, Long warehouseId) {

        Store store = Store.findById(storeId);
        if (store == null) {
            throw new IllegalArgumentException(
                    "Store with id " + storeId + " does not exist.");
        }

        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException(
                    "Product with id " + productId + " does not exist.");
        }

        DbWarehouse warehouse = warehouseRepository.findById(warehouseId);
        if (warehouse == null || warehouse.archivedAt != null) {
            throw new IllegalArgumentException(
                    "Warehouse with id " + warehouseId + " does not exist.");
        }

        if (repository.exists(storeId, productId, warehouseId)) {
            throw new IllegalArgumentException(
                    "This Store, Product and Warehouse association already exists.");
        }

        long warehousesForProductInStore =
                repository.countWarehousesForStoreAndProduct(storeId, productId);

        if (warehousesForProductInStore >= 2) {
            throw new IllegalArgumentException(
                    "A Product can be fulfilled by a maximum of 2 Warehouses per Store.");
        }

        long warehousesForStore =
                repository.countWarehousesForStore(storeId);

        if (warehousesForStore >= 3) {
            throw new IllegalArgumentException(
                    "A Store can be fulfilled by a maximum of 3 Warehouses.");
        }

        long productsInWarehouse =
                repository.countProductsForWarehouse(warehouseId);

        if (productsInWarehouse >= 5) {
            throw new IllegalArgumentException(
                    "A Warehouse can store a maximum of 5 Products.");
        }

        StoreProductWarehouse association =
                new StoreProductWarehouse(store, product, warehouse);

        repository.persist(association);
    }
}