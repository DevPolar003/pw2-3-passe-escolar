CREATE DATABASE Passe_Escolar
USE Passe_Escolar

CREATE TABLE Carteirinha(
Id BIGINT IDENTITY(1,1) PRIMARY KEY,
Nome_estudante VARCHAR(100) NOT NULL,
Matricula VARCHAR(30) NOT NULL,
Linha_onibus VARCHAR(40) NOT NULL,
Data_solicitacao DATE NOT NULL,
Data_retirada_prevista DATE,
Status VARCHAR(25) NOT NULL
)
