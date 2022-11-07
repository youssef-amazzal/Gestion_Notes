create table if not exists Etudiant (
    id integer primary key,
    nom text,
    prenom text,
    date_naissance text
);

create table if not exists semestre (
    id integer primary key,
    libelle text,
    numero integer,
    date_debut text,
    date_fin text
);

create table if not exists Filiere (
    id integer primary key,
    libelle text
);

create table if not exists Module (
    id integer primary key,
    libelle text,
    coefficient integer,
    nombre_heure integer,
    id_filiere integer,
    id_semestre integer,
    constraint module_filiere_fk FOREIGN KEY (id_filiere) references Filiere(id),
    constraint module_semestre_fk FOREIGN KEY (id_semestre) references semestre(id)
);

create table if not exists Examen (
    note real,
    id_module integer,
    id_etudiant integer,
    constraint examen_pk PRIMARY KEY (id_module,id_etudiant),
    constraint examen_etudiant_fk FOREIGN KEY (id_etudiant) references Etudiant(id),
    constraint examen_module_fk FOREIGN KEY (id_module) references Module(id)
);