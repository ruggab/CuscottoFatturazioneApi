CREATE TABLE [dbo].[stato_fattura_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_fattura] [nvarchar](10) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[stato_fattura] [nvarchar](1) NOT NULL
) ON [PRIMARY]