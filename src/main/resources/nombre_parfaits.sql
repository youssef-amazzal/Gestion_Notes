declare
    somme number := 1;
begin
    for i in 1..1000 loop
        for j in 1..(i-1) loop
            if i mod j = 0 then
                somme := somme + j;
            end if;
        end loop;
        if somme = i then
            DBMS_OUTPUT.PUT_LINE(i);
        end if;
        somme:= 0;
    end loop;
end;

drop table DIVISEURS;

-- create the table
CREATE table diviseurs
(
    nbre     number,
    diviseur number
);
-- insert in the table
declare
begin
    for i in 1..1000 loop
        insert into diviseurs values (i,1);
        for j in 2..(i-1) loop
            if i mod j = 0 then
                insert into diviseurs values (i,j);
            end if;
        end loop;
    end loop;
end;
commit ;

-- avec group by et sans curseur
declare
    somme number;
begin
    for i in 1..1000 loop
        select sum(diviseur) into somme
            from DIVISEURS
            where nbre=i
            group by nbre;
        if (somme = i) then
            DBMS_OUTPUT.PUT_LINE(i);
        end if;
    end loop;
end;

-- sans group et sans curseur
declare
    somme number;
begin
    for i in 1..1000 loop
        select sum(diviseur) into somme
            from DIVISEURS
            where nbre=i;
        if (somme = i) then
            DBMS_OUTPUT.PUT_LINE(i);
        end if;
    end loop;
end;


-- sans group by avec curseur
declare
    somme number :=0;
begin
    for i in 1..1000 loop

        declare
            cursor cursor_diviseurs is
                select distinct diviseur
                from diviseurs where nbre=i;
        begin
            for record in cursor_diviseurs loop
                somme := somme + record.diviseur;
            end loop;
            if somme = i then
                DBMS_OUTPUT.PUT_LINE(i);
            end if;
            somme := 0;
        end;

    end loop;
end;


-- curseur parametre
declare
    somme number :=0;
    cursor cursor_diviseurs(nombre number) is
        select distinct diviseur
        from diviseurs where nbre=nombre;
begin
    for i in 1..1000 loop
        for diviseur_value in cursor_diviseurs(i) loop
            somme := somme + diviseur_value.diviseur;
        end loop;
        if somme = i then
            DBMS_OUTPUT.PUT_LINE(i);
        end if;
        somme := 0;
    end loop;
end;



