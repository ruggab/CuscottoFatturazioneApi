ALTER TABLE [dbo].[articolo]
ADD UNIQUE (codice_articolo); 


ALTER TABLE [dbo].[tipologia_corrispettivi]
ADD UNIQUE (codice_corrispettivo); 


ALTER TABLE [dbo].[societa]
ADD UNIQUE (codice_societa); 