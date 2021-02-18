INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-01', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('John', 'Doe', 'john.doe@gmail.com', '2017-08-02', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2017-08-03', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Jane', 'Doe', 'jane.doe@gmail.com', '2017-08-04', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2017-08-05', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2017-08-06', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2017-08-07', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2017-08-08', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2017-08-09', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('James', 'Gosling', 'james.gosling@gmail.com', '2017-08-010', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Bruce', 'Lee', 'bruce.lee@gmail.com', '2017-08-11', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Johnny', 'Doe', 'johnny.doe@gmail.com', '2017-08-12', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('John', 'Roe', 'john.roe@gmail.com', '2017-08-13', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Jane', 'Roe', 'jane.roe@gmail.com', '2017-08-14', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Richard', 'Doe', 'richard.doe@gmail.com', '2017-08-15', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Janie', 'Doe', 'janie.doe@gmail.com', '2017-08-16', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Phillip', 'Webb', 'phillip.webb@gmail.com', '2017-08-17', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Stephane', 'Nicoll', 'stephane.nicoll@gmail.com', '2017-08-18', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Sam', 'Brannen', 'sam.brannen@gmail.com', '2017-08-19', '');  
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Juergen', 'Hoeller', 'juergen.Hoeller@gmail.com', '2017-08-20', ''); 
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Janie', 'Roe', 'janie.roe@gmail.com', '2017-08-21', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('John', 'Smith', 'john.smith@gmail.com', '2017-08-22', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2017-08-23', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('John', 'Stiles', 'john.stiles@gmail.com', '2017-08-24', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Richard', 'Roe', 'stiles.roe@gmail.com', '2017-08-25', '');
INSERT INTO cliente (nombre, apellido, email, fecha_registro, foto) VALUES('Antonio', 'Perez', 'antonio@mail.com', '2019-05-03', '');

INSERT INTO productos (nombre, precio, create_at) VALUES('Panasonic Pantalla LCD', 259990, '2019-05-03');
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Camara digital DSC-W320B', 123490, '2017-08-25');
INSERT INTO productos (nombre, precio, create_at) VALUES('Apple iPod shuffle', 1499990, '2017-08-24');
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Notebook Z110', 37990, '2017-08-22');
INSERT INTO productos (nombre, precio, create_at) VALUES('Hewlett Packard Multifuncional F2280', 69990, '2017-08-21');
INSERT INTO productos (nombre, precio, create_at) VALUES('Bianchi Bicicleta Aro 26', 69990, '2017-08-20');
INSERT INTO productos (nombre, precio, create_at) VALUES('Mica Comoda 5 Cajones', 299990, '2017-08-18');

INSERT INTO factura (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());
INSERT INTO items_factura (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO items_factura (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO items_factura (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO items_factura (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO factura (descripcion, observacion, cliente_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO items_factura (cantidad, factura_id, producto_id) VALUES(3, 2, 6);

INSERT INTO users(username, password, enabled) VALUES('admin', '$2a$10$S.QqPXcUg55.gVn5eAN6vu0hvsZ3ShkCnhsZT/akv0WKN8y9d68lO', 1);
INSERT INTO users(username, password, enabled) VALUES('antonio', '$2a$10$2YbxEGLjTK1HwkYBTiwSR.TOo2KnFxDvclMfdWsQaC9YllCmNBFmW', 1);

INSERT INTO rol(name_role, user_id) VALUES('ROLE_USER', 1);
INSERT INTO rol(name_role, user_id) VALUES('ROLE_ADMIN', 1);
INSERT INTO rol(name_role, user_id) VALUES('ROLE_USER', 2);

