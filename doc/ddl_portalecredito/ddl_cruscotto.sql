CREATE TABLE [dbo].[cliente](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[codice_cliente] [nvarchar](10) NOT NULL,
	[societa] [nvarchar](4) NULL,
	[ragione_sociale] [nvarchar](35) NOT NULL,
	[codice_fiscale] [nvarchar](16)  NULL,
	[partita_iva] [nvarchar](11)  NULL,
	[nazionalita] [nvarchar](3) NOT NULL,
	[sede_legale] [nvarchar](35) NOT NULL,
	[appartiene_gruppo_iva] [nvarchar](1) NOT NULL,
	[codice_destinatario_fatturazione] [nvarchar](18) NOT NULL,
	[modalita_pagamento] [nvarchar](10) NOT NULL,
	[condizioni_pagamento] [nvarchar](4) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]



CREATE TABLE [dbo].[societa](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[codice_societa] [nvarchar](4) NOT NULL,
	[descrizione] [nvarchar](35) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]



CREATE TABLE [dbo].[articolo](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[codice_articolo] [nvarchar](4) NOT NULL,
	[descrizione] [nvarchar](50) NOT NULL,
	[data_validita] [datetime] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]



CREATE TABLE [dbo].[tipologia_corrispettivi](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[codice_corrispettivo] [nvarchar](3) NOT NULL,
	[descrizione] [nvarchar](35) NOT NULL,
	[data_validita] [datetime] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]

CREATE TABLE [dbo].[fattura](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[societa] [nvarchar](4) NOT NULL,
	[data_fattura] [datetime] NOT NULL,
	[tipologia_fattura] [nvarchar](2) NOT NULL,
	[codice_cliente] [nvarchar](10) NOT NULL,
	[importo] [numeric](14,2) NOT NULL,
	[stato_fattura] [nvarchar](1) NULL,
	[esito_invio] [nvarchar](1)  NULL,
	[data_invio_flusso] [datetime] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]


CREATE TABLE [dbo].[dettaglio_fattura](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_fattura] [int] NOT NULL,
	[progressivo_riga] [int] NOT NULL,
	[codice_articolo] [nvarchar](3) NOT NULL,
	[codice_corrispettivo] [nvarchar](3) NOT NULL,
	[importo] [numeric](14,2) NOT NULL,
	[note] [nvarchar](30) NULL,
	[id_messaggio] [nvarchar](3) NULL,
	[descrizione_messaggio] [nvarchar](50)  NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]


