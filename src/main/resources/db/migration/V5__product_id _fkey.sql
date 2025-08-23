alter table order_items
    add constraint order_items_products_id_fk
        foreign key (product_id) references products (id);