CREATE TABLE IF NOT EXISTS Customers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    phone TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Components (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    component_type TEXT NOT NULL CHECK (component_type IN ('case', 'core')),
    material TEXT NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    UNIQUE (component_type, material)
);

CREATE TABLE IF NOT EXISTS Items (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    case_component_id INTEGER NOT NULL,
    core_component_id INTEGER NOT NULL,
    price REAL NOT NULL,
    available BOOLEAN NOT NULL DEFAULT 1,
    FOREIGN KEY (case_component_id) REFERENCES Components(id),
    FOREIGN KEY (core_component_id) REFERENCES Components(id)
);

CREATE TABLE IF NOT EXISTS Purchases (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id INTEGER NOT NULL,
    item_id INTEGER NOT NULL,
    purchase_date DATE NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Customers(id),
    FOREIGN KEY (item_id) REFERENCES Items(id)
);

CREATE TABLE IF NOT EXISTS Supplies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    supplier_name TEXT NOT NULL,
    supply_date DATE NOT NULL,
    component_type TEXT NOT NULL CHECK (component_type IN ('case', 'core')),
    material TEXT NOT NULL,
    quantity INTEGER NOT NULL
);

CREATE TRIGGER after_supply_insert
    AFTER INSERT ON Supplies
BEGIN
    INSERT INTO Components (component_type, material, quantity)
    VALUES (NEW.component_type, NEW.material, NEW.quantity)
    ON CONFLICT(component_type, material) DO UPDATE SET
        quantity = quantity + EXCLUDED.quantity;
END;