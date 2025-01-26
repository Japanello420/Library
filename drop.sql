
    alter table books_authors 
       drop 
       foreign key FKov77rfnon6gwd8emlsah05qty;

    alter table books_authors 
       drop 
       foreign key FK6ojkw2gy23xsgdkqih628favt;

    alter table books_genres 
       drop 
       foreign key FKm0yqdd4t2neamf9s7pe9pqg19;

    alter table books_genres 
       drop 
       foreign key FKih47hjfsl8cfacjhocsvbipfe;

    drop table if exists author;

    drop table if exists book;

    drop table if exists books_authors;

    drop table if exists books_genres;

    drop table if exists genre;
