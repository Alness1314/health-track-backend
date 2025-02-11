
CREATE TABLE suppliers (
    id UUID PRIMARY KEY,  
    name VARCHAR(255) NOT NULL,  -- Nombre del proveedor
    contact_name VARCHAR(255),  -- Contacto principal
    phone VARCHAR(20),  -- Teléfono
    email VARCHAR(255),  -- Correo electrónico
    address TEXT,  -- Dirección del proveedor
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    erased BOOL DEFAULT FALSE
);

CREATE TABLE purchase_orders (
    id UUID PRIMARY KEY,  
    supplier_id UUID NOT NULL,  -- Relación con proveedor
    order_date TIMESTAMP DEFAULT NOW(),  -- Fecha del pedido
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',  -- Estado del pedido (PENDING, RECEIVED, CANCELED)
    total DECIMAL(10,2) NOT NULL DEFAULT 0,  -- Monto total de la orden
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    erased BOOL DEFAULT FALSE,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
);

CREATE TABLE inventory (
    id UUID PRIMARY KEY,  
    name VARCHAR(255) NOT NULL,  -- Nombre del material
    description TEXT,  -- Descripción del producto
    quantity INT NOT NULL DEFAULT 0,  -- Cantidad en stock
    unit VARCHAR(50) NOT NULL,  -- Unidad de medida (ej. cajas, piezas)
    min_stock INT NOT NULL DEFAULT 0,  -- Stock mínimo antes de reabastecer
    price DECIMAL(10,2),  -- Precio unitario estimado
    supplier_id UUID,  -- Relación con el proveedor
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    erased BOOL DEFAULT FALSE,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
);


CREATE TABLE purchase_order_items (
    id UUID PRIMARY KEY,  
    purchase_order_id UUID NOT NULL,  -- Relación con orden de compra
    inventory_id UUID NOT NULL,  -- Relación con inventario
    quantity INT NOT NULL,  -- Cantidad pedida
    unit_price DECIMAL(10,2) NOT NULL,  -- Precio unitario del producto
    subtotal DECIMAL(10,2) NOT NULL,  -- Cantidad * precio unitario
    FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders(id),
    FOREIGN KEY (inventory_id) REFERENCES inventory(id)
);