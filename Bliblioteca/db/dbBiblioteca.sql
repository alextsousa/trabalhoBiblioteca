/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  AlexSousa
 * Created: 09/11/2016
 */


create table titulos (
    id serial primary key,
    titulo varchar(50),
    autor varchar(50)
);

create table exemplares (
    id serial primary key,
    status boolean,
    dataCad timestamp,
    titulo_id integer references titulos(id)
);

create table usuarios(
    id serial primary key,
    nome varchar(50),
    email varchar(50)
);


