package org.example;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopRepository extends CrudRepository<Shop, Long> {
    List<Shop> findByShopNameContainingIgnoreCase(String name);
}
