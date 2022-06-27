USE [master]
GO

CREATE DATABASE [PortaleCredito]
GO

USE [PortaleCredito]
GO
/****** Object:  Schema [Anagraphics]    Script Date: 21/06/2022 12:11:36 ******/
CREATE SCHEMA [Anagraphics]
GO
/****** Object:  Schema [Logs]    Script Date: 21/06/2022 12:11:36 ******/
CREATE SCHEMA [Logs]
GO
/****** Object:  Schema [Procedural]    Script Date: 21/06/2022 12:11:36 ******/
CREATE SCHEMA [Procedural]
GO
/****** Object:  Schema [Statistics]    Script Date: 21/06/2022 12:11:36 ******/
CREATE SCHEMA [Statistics]
GO
/****** Object:  Table [dbo].[sale_richiesta]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sale_richiesta](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[richiesta_id] [int] NOT NULL,
	[codice_sala] [nvarchar](50) NOT NULL,
	[numero_macchine] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_sale_richiesta] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tir_sala]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tir_sala](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[sala_id] [int] NOT NULL,
	[codice_tir] [nvarchar](50) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_tir_sala] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[VW_Sale_TIR_Richieste]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE VIEW [dbo].[VW_Sale_TIR_Richieste]
AS

SELECT [S_R].[id] [sala_id]
      ,[richiesta_id]
      ,[codice_sala]
      ,[numero_macchine]
      ,[S_R].[create_user] [create_user_sala]
      ,[S_R].[create_date] [create_date_sala]
      ,[S_R].[last_mod_user] [last_mod_user_sala]
      ,[S_R].[last_mod_date] [last_mod_date_sala]
	  ,[T_S].[id] [tir_id]
      ,[codice_tir]
      ,[T_S].[create_user] [create_user_tir]
      ,[T_S].[create_date] [create_date_tir]
      ,[T_S].[last_mod_user] [last_mod_user_tir]
      ,[T_S].[last_mod_date] [last_mod_date_tir]
  FROM [dbo].[sale_richiesta] [S_R]
  JOIN [dbo].[tir_sala] [T_S] ON [S_R].[id] = [T_S].[sala_id]

GO
/****** Object:  Table [dbo].[Workflow_step]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Workflow_step](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[workflow_id] [int] NULL,
	[nome_step] [nvarchar](100) NULL,
	[tab_index] [int] NULL,
	[attivato] [bit] NULL,
	[notifica] [bit] NULL,
	[definitivo] [bit] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Workflow]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Workflow](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nome_flusso] [nvarchar](100) NULL,
	[business] [nvarchar](10) NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [Procedural].[FC_GetDefaultWorkflowStep]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 27/01/2022
-- Description:	Carica lo il workflow di default in base al business
-- =============================================
CREATE FUNCTION [Procedural].[FC_GetDefaultWorkflowStep]
(	
	@businessName NVARCHAR(10)
)
RETURNS TABLE 
AS
RETURN 
(
	-- Add the SELECT statement with parameter references here
	SELECT TOP(1) 
		[WS].[id] [id],
		[WS].[tab_index] [tab_index]
	FROM
		[dbo].[Workflow_step] [WS]
	JOIN
		[dbo].[Workflow] [W] ON [W].[id] = [WS].[workflow_id]
	WHERE
		[W].[business] = @businessName
 ORDER BY
		[WS].[tab_index] ASC
)
GO
/****** Object:  Table [dbo].[richiesta]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[richiesta](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[cliente_id] [nvarchar](50) NOT NULL,
	[codice_sala] [nvarchar](50) NOT NULL,
	[codice_tir] [nvarchar](50) NOT NULL,
	[workflow_step_id] [int] NOT NULL,
	[stato_id] [int] NULL,
	[stato_sap_id] [int] NULL,
	[data_attivazione] [datetime] NULL,
	[categoria_id] [int] NULL,
	[presenza_societa_collegate] [bit] NULL,
	[societa_collegate] [nvarchar](500) NULL,
	[presenza_ultimo_bilancio] [bit] NULL,
	[anno_bilancio] [int] NULL,
	[presenza_eventi_negativi] [bit] NULL,
	[eventi_negativi] [nvarchar](500) NULL,
	[presenza_esiti_pregressi] [bit] NULL,
	[side_letter] [bit] NULL,
	[canale_contratto_id] [int] NULL,
	[canale_sottoscrizione_id] [int] NULL,
	[deroga_contratto_id] [int] NULL,
	[deroga_merito_user] [nvarchar](50) NULL,
	[deroga_merito_date] [datetime] NULL,
	[deroga_merito_id] [int] NULL,
	[deroga_merito_nota_id] [int] NULL,
	[salvata] [bit] NULL,
	[work_user_id] [int] NULL,
	[work_user] [nvarchar](50) NULL,
	[work_date] [datetime] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[stato_richiesta]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[stato_richiesta](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nome] [nvarchar](50) NOT NULL,
	[definitivo] [bit] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[cliente]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cliente](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[codice_cliente] [nvarchar](50) NOT NULL,
	[nome_cliente] [nvarchar](500) NULL,
	[business] [nvarchar](500) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[Business]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Anagraphics].[Business](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_Business] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[Cliente_Business]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Anagraphics].[Cliente_Business](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[cliente_id] [int] NOT NULL,
	[business_id] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_Cliente_Business] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[VW_ClientiInattivi]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO






CREATE VIEW [dbo].[VW_ClientiInattivi]
AS

SELECT
	[R].[id],
	[C].[codice_cliente],
	[C].[nome_cliente],
	[BU].[name] AS [business],
	[workflow_step_id],
	CASE
		WHEN ISNULL([S_R].[nome],'') = 'Attivo' THEN 'attivo'
		WHEN ISNULL([S_R].[nome],'') = 'Attivo con Deroga' THEN 'attivo deroga'
		WHEN ISNULL([S_R].[nome],'') = 'Negativo' THEN 'ko_valutazione_merito'
		ELSE [WS].[nome_step]
	END AS [nome_step],
	0 AS [Somma_Garanzie],
	ISNULL([R].[last_mod_date],[R].[create_date]) AS [last_mod]
FROM
	[dbo].[cliente] [C]
JOIN
	[dbo].[richiesta] [R]
ON
	[C].[id] = [R].[cliente_id]
JOIN
	[dbo].[Workflow_step] [WS]
ON
	[WS].[id] = [workflow_step_id]
JOIN
	[Anagraphics].[Cliente_Business] [C_B]
ON
	[C_B].[cliente_id] = c.[id]
JOIN
	[Anagraphics].[Business] [BU]
ON
	[BU].[id] = [C_B].[business_id]
LEFT JOIN
	[dbo].[stato_richiesta] [S_R]
ON
	[S_R].[id] = [R].[stato_id]
WHERE
	[R].[stato_sap_id] IS NULL
AND 
	ISNULL([WS].[tab_index],-1) >= 3

GO
/****** Object:  View [dbo].[VW_ClientiAttivi]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO






CREATE VIEW [dbo].[VW_ClientiAttivi]
AS

SELECT
	[R].[id],
	[C].[codice_cliente],
	[C].[nome_cliente],
	[BU].[name] AS [business],
	[workflow_step_id],
	CASE
		WHEN ISNULL([S_R].[nome],'') = 'Attivo' THEN 'attivo'
		WHEN ISNULL([S_R].[nome],'') = 'Attivo con Deroga' THEN 'attivo deroga'
		WHEN ISNULL([S_R].[nome],'') = 'Negativo' THEN 'ko_valutazione_merito'
		ELSE [WS].[nome_step]
	END AS [nome_step],
	0 AS [Somma_Garanzie],
	ISNULL([R].[last_mod_date],[R].[create_date]) AS [last_mod]
FROM
	[dbo].[cliente] [C]
JOIN
	[dbo].[richiesta] [R]
ON
	[C].[id] = [R].[cliente_id]
JOIN
	[dbo].[Workflow_step] [WS]
ON
	[WS].[id] = [workflow_step_id]
JOIN
	[Anagraphics].[Cliente_Business] [C_B]
ON
	[C_B].[cliente_id] = c.[id]
JOIN
	[Anagraphics].[Business] [BU]
ON
	[BU].[id] = [C_B].[business_id]
LEFT JOIN
	[dbo].[stato_richiesta] [S_R]
ON
	[S_R].[id] = [R].[stato_id]
WHERE
	[R].[stato_sap_id] IS NOT NULL
AND 
	ISNULL([WS].[tab_index],-1) >= 3

GO
/****** Object:  Table [Statistics].[StatoScadenzaGaranzia]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Statistics].[StatoScadenzaGaranzia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[garanzia_id] [int] NOT NULL,
	[in_scadenza] [bit] NOT NULL,
	[scaduta] [bit] NOT NULL,
	[alert_scadenza] [datetime] NULL,
	[alert_scaduta] [datetime] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_StatoScadenzaGaranzia] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[garanzie]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[garanzie](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[richiesta_id] [int] NOT NULL,
	[codice_garanzia] [nvarchar](50) NOT NULL,
	[stato_id] [int] NULL,
	[tipologia_id] [int] NULL,
	[validation_id] [uniqueidentifier] NULL,
	[deroga] [bit] NULL,
	[note_deroga] [nvarchar](max) NULL,
	[prestato] [decimal](14, 2) NULL,
	[dovuto] [decimal](14, 2) NULL,
	[protocollo] [nvarchar](50) NULL,
	[diritto] [nvarchar](50) NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  View [dbo].[vw_uff_garanzie_dash_numeri]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE VIEW [dbo].[vw_uff_garanzie_dash_numeri]
AS
SELECT 1 as box, count(*) as primario, convert(varchar(19), ISNULL(max(r.[last_mod_date]),max(r.[create_date])), 120) as secondario 
FROM     dbo.cliente 
JOIN dbo.richiesta r ON dbo.cliente.id = r.cliente_id 
JOIN [Anagraphics].[Cliente_Business] [C_B] ON [C_B].[cliente_id] = dbo.cliente.[id]
JOIN [Anagraphics].[Business] [B] ON [B].[id] = [C_B].[business_id]
where stato_id = -1 
OR  stato_id IS NULL
OR stato_id = (
				SELECT [id] FROM [Procedural].[FC_GetDefaultWorkflowStep]([B].[name])
			  )
UNION
SELECT 2 as box,  (select count(*) FROM  dbo.richiesta where stato_id in (1,4)),convert(varchar(50),(select count(*) FROM  dbo.richiesta where NOT stato_id is null))
UNION
SELECT 3 as box,
	   COUNT([G].[id]),
       convert(varchar(50),ISNULL(SUM([G].[dovuto]),0))
  FROM [PortaleCredito].[dbo].[garanzie] [G]
  JOIN [Statistics].[StatoScadenzaGaranzia] [SG] ON [SG].[garanzia_id] = [G].[id]
  WHERE [SG].[in_scadenza] = 1
    AND [SG].[scaduta] = 0
-- SELECT 3 as box,  (select count(*) FROM  dbo.garanzie where stato_id =1),'12500'
UNION
SELECT 4 AS box,
	   COUNT([G].[id]),
	   convert(varchar(19),MAX([G].[last_mod_date]),120) as secondario
  FROM [dbo].[garanzie] [G]
  JOIN [dbo].[richiesta] [R] ON [G].[richiesta_id] = [R].[id]
 WHERE [G].[create_user] = 'ImportNewGaranzieAPI'
   AND [R].[stato_id] != (
							 SELECT TOP(1) [id]
									  FROM [dbo].[stato_richiesta]
									 WHERE [nome] = 'Attivo'
						 )
UNION
SELECT 5 as box,
	   COUNT([G].[id]),
       convert(varchar(50),ISNULL(SUM([G].[dovuto]),0))
  FROM [PortaleCredito].[dbo].[garanzie] [G]
  JOIN [Statistics].[StatoScadenzaGaranzia] [SG] ON [SG].[garanzia_id] = [G].[id]
  WHERE [SG].[scaduta] = 1
GO
/****** Object:  View [dbo].[vw_top_10_prespect]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO











CREATE VIEW [dbo].[vw_top_10_prespect]
AS



select top 10 
	r.id, 
	c.codice_cliente,
	c.nome_cliente, 
	[BU].[name] AS business, 
	workflow_step_id,
	CASE
		WHEN ISNULL([S_R].[nome],'') = 'Attivo' THEN 'attivo'
		WHEN ISNULL([S_R].[nome],'') = 'Attivo con Deroga' THEN 'attivo deroga'
		WHEN ISNULL([S_R].[nome],'') = 'Negativo' THEN 'ko_valutazione_merito'
		ELSE [WS].[nome_step]
	END AS [nome_step],
	0 as somma_garanzie,
	ISNULL([R].[last_mod_date],[R].[create_date]) AS [last_mod]
from [dbo].[cliente] c 
inner join dbo.richiesta r
on c.id = r.cliente_id
inner join dbo.Workflow_step ws
on ws.id = workflow_step_id
JOIN [Anagraphics].[Cliente_Business] [C_B]
ON [C_B].[cliente_id] = c.[id]
JOIN [Anagraphics].[Business] [BU]
ON [BU].[id] = [C_B].[business_id]
LEFT JOIN
	[dbo].[stato_richiesta] [S_R]
ON
	[S_R].[id] = [R].[stato_id]
where ws.tab_index < 3
order by c.create_date desc 

GO
/****** Object:  View [dbo].[vw_top_10_clienti]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[vw_top_10_clienti]
AS

SELECT TOP(10)
		[R].[id],
		[C].[codice_cliente],
		[C].[nome_cliente],
		[BU].[name] AS [business],
		[workflow_step_id],
		CASE
			WHEN ISNULL([S_R].[nome],'') = 'Attivo' THEN 'attivo'
			WHEN ISNULL([S_R].[nome],'') = 'Attivo con Deroga' THEN 'attivo deroga'
			WHEN ([WS].[nome_step] = 'merito creditizio' AND ISNULL([S_R].[nome],'') = 'Negativo') THEN 'ko_valutazione_merito'
			ELSE [WS].[nome_step]
		END AS [nome_step],
		(ABS(CHECKSUM(NewId())) % 50000) as [Somma_Garanzie],
		ISNULL([R].[last_mod_date],[R].[create_date]) AS [last_mod],
		[R].[work_user_id]
	FROM
		[dbo].[cliente] [C]
	JOIN
		[dbo].[richiesta] [R]
	ON
		[C].[id] = [R].[cliente_id]
	JOIN
		[dbo].[Workflow_step] [WS]
	ON
		[WS].[id] = [workflow_step_id]
	JOIN
		[Anagraphics].[Cliente_Business] [C_B]
	ON
		[C_B].[cliente_id] = c.[id]
	JOIN
		[Anagraphics].[Business] [BU]
	ON
		[BU].[id] = [C_B].[business_id]
	LEFT JOIN
		[dbo].[stato_richiesta] [S_R]
	ON
		[S_R].[id] = [R].[stato_id]
	WHERE
		ISNULL([WS].[tab_index],-1) >= 3
	ORDER BY
		[C].[create_date] DESC 

GO
/****** Object:  View [dbo].[VW_ALL_Clienti]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE VIEW [dbo].[VW_ALL_Clienti]
AS

	SELECT
		[R].[id],
		[C].[codice_cliente],
		[C].[nome_cliente],
		[BU].[name] AS [business],
		[workflow_step_id],
		CASE
			WHEN ISNULL([S_R].[nome],'') = 'Attivo' THEN 'attivo'
			WHEN ISNULL([S_R].[nome],'') = 'Attivo con Deroga' THEN 'attivo deroga'
			WHEN ([WS].[nome_step] = 'merito creditizio' AND ISNULL([S_R].[nome],'') = 'Negativo') THEN 'ko_valutazione_merito'
			ELSE [WS].[nome_step]
		END AS [nome_step],
		0 AS [Somma_Garanzie],
		ISNULL([R].[last_mod_date],[R].[create_date]) AS [last_mod],
		[R].[work_user_id],
		[in_scadenza] = CASE WHEN EXISTS (
									SELECT *
									  FROM [PortaleCredito].[dbo].[garanzie] [G]
									  JOIN [Statistics].[StatoScadenzaGaranzia] [SG] ON [SG].[garanzia_id] = [G].[id]
									 WHERE [SG].[in_scadenza] = 1
									   AND [SG].[scaduta] = 0
									   AND [G].[richiesta_id] = [R].[id]
									)
									THEN 1
									ELSE 0
							END,
		[scaduta] = CASE WHEN EXISTS (
									SELECT *
									  FROM [PortaleCredito].[dbo].[garanzie] [G]
									  JOIN [Statistics].[StatoScadenzaGaranzia] [SG] ON [SG].[garanzia_id] = [G].[id]
									 WHERE [SG].[scaduta] = 1
									   AND [G].[richiesta_id] = [R].[id]
									)
									THEN 1
									ELSE 0
							END,
		[rinnovo] = CASE WHEN EXISTS (
									SELECT *
									  FROM [dbo].[garanzie] [G]
									  JOIN [dbo].[richiesta] [R_IN] ON [G].[richiesta_id] = [R_IN].[id]
									 WHERE [G].[create_user] = 'ImportNewGaranzieAPI'
									   AND [R_IN].[stato_id] != (
															 SELECT TOP(1) [id]
																	  FROM [dbo].[stato_richiesta]
																	 WHERE [nome] = 'Attivo'
														 )
									   AND [G].[richiesta_id] = [R].[id]
									)
									THEN 1
									ELSE 0
							END
	FROM
		[dbo].[cliente] [C]
	JOIN
		[dbo].[richiesta] [R]
	ON
		[C].[id] = [R].[cliente_id]
	JOIN
		[dbo].[Workflow_step] [WS]
	ON
		[WS].[id] = [workflow_step_id]
	JOIN
		[Anagraphics].[Cliente_Business] [C_B]
	ON
		[C_B].[cliente_id] = c.[id]
	JOIN
		[Anagraphics].[Business] [BU]
	ON
		[BU].[id] = [C_B].[business_id]
	LEFT JOIN
		[dbo].[stato_richiesta] [S_R]
	ON
		[S_R].[id] = [R].[stato_id]
	WHERE
		ISNULL([WS].[tab_index],-1) >= 3

GO
/****** Object:  View [dbo].[VW_ALL_Prospect]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE VIEW [dbo].[VW_ALL_Prospect]
AS

SELECT
	[R].[id],
	[C].[codice_cliente],
	[C].[nome_cliente],
	[BU].[name] AS [business],
	[workflow_step_id],
	CASE
		WHEN ISNULL([S_R].[nome],'') = 'Attivo' THEN 'attivo'
		WHEN ISNULL([S_R].[nome],'') = 'Attivo con Deroga' THEN 'attivo deroga'
		WHEN ([WS].[nome_step] = 'merito creditizio' AND ISNULL([S_R].[nome],'') = 'Negativo') THEN 'ko_valutazione_merito'
		ELSE [WS].[nome_step]
	END AS [nome_step],
	0 AS [Somma_Garanzie],
	ISNULL([R].[last_mod_date],[R].[create_date]) AS [last_mod],
	[R].[work_user_id]
FROM
	[dbo].[cliente] [C]
JOIN
	[dbo].[richiesta] [R]
ON
	[C].[id] = [R].[cliente_id]
JOIN
	[dbo].[Workflow_step] [WS]
ON
	[WS].[id] = [workflow_step_id]
JOIN
	[Anagraphics].[Cliente_Business] [C_B]
ON
	[C_B].[cliente_id] = c.[id]
JOIN
	[Anagraphics].[Business] [BU]
ON
	[BU].[id] = [C_B].[business_id]
LEFT JOIN
	[dbo].[stato_richiesta] [S_R]
ON
	[S_R].[id] = [R].[stato_id]
WHERE
	ISNULL([WS].[tab_index],-1) < 3

GO
/****** Object:  Table [dbo].[tipologia_documento]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipologia_documento](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tipologia] [nvarchar](250) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[documenti_cliente]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[documenti_cliente](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[richiesta_id] [int] NOT NULL,
	[tipo_doc_id] [int] NOT NULL,
	[doc_name] [nvarchar](500) NULL,
	[doc_path] [nvarchar](2500) NULL,
	[doc_blob] [varbinary](max) NULL,
	[doc_type] [nvarchar](50) NULL,
	[doc_ext] [nvarchar](10) NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  View [dbo].[VW_Documenti_Richieste]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO




CREATE VIEW [dbo].[VW_Documenti_Richieste]
AS

	SELECT [DC].[id] [document_id]
		  ,[DC].[richiesta_id]
		  ,[DC].[tipo_doc_id] [tipo_documento_id]
		  ,[TD].[tipologia] [tipo_documento]
		  ,[DC].[doc_name]
		  ,[DC].[doc_path]
		  ,[DC].[doc_blob]
		  ,[DC].[doc_type]
		  ,[DC].[doc_ext]
		  ,ISNULL([DC].[last_mod_user],[DC].[create_user]) [last_mode_user]
		  ,ISNULL([DC].[last_mod_date],[DC].[create_date]) [last_mode_date]
		  ,[is_edit] = CASE WHEN [DC].[last_mod_date] IS NULL THEN 0 ELSE 1 END
	  FROM [dbo].[documenti_cliente] [DC]
	  JOIN [dbo].[tipologia_documento] [TD] ON [DC].[tipo_doc_id] = [TD].[id]


GO
/****** Object:  Table [dbo].[stato_insoluti]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[stato_insoluti](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nome] [nvarchar](50) NOT NULL,
	[indice] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_stato_insoluti] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[insoluti]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[insoluti](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[richiesta_id] [int] NOT NULL,
	[stato_id] [int] NULL,
	[corrente] [decimal](14, 2) NOT NULL,
	[scaduto] [decimal](14, 2) NOT NULL,
	[esposizione] [decimal](14, 2) NOT NULL,
	[data_riferimento] [datetime] NOT NULL,
	[scadenza_rendimento] [datetime] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_insoluti] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[VW_ALL_Insoluti]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[VW_ALL_Insoluti]
AS

	SELECT [IN].[id] [id]
		  ,[IN].[richiesta_id]
		  ,[C].[codice_cliente]
		  ,[C].[nome_cliente]
		  ,[B].[name] [business]
		  ,[IN].[stato_id]
		  ,[S_I].[nome] [stato_insoluto]
		  ,[IN].[corrente]
		  ,[IN].[scaduto]
		  ,[IN].[esposizione]
		  ,[IN].[data_riferimento]
		  ,[IN].[scadenza_rendimento]
		  ,[IN].[create_user]
		  ,[IN].[create_date]
		  ,[IN].[last_mod_user]
		  ,[IN].[last_mod_date]
	  FROM [dbo].[insoluti] [IN]
	  JOIN [dbo].[richiesta] [R] ON [IN].[richiesta_id] = [R].[id]
	  JOIN [dbo].[cliente] [C] ON [R].[cliente_id] = [C].[id]
	  JOIN [Anagraphics].[Cliente_Business] [C_B] ON [C_B].[cliente_id] = [C].[id]
	  JOIN [Anagraphics].[Business] [B] ON [B].[id] = [C_B].[business_id]
	  JOIN [dbo].[stato_insoluti] [S_I] ON [S_I].[id] = [IN].[stato_id]

GO
/****** Object:  Table [Anagraphics].[Categorie_Richieste]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Anagraphics].[Categorie_Richieste](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nome] [nvarchar](100) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_Categorie_Richieste] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[Gruppi]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Anagraphics].[Gruppi](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_Gruppi] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[Notifiche_TipologiaNotifiche]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Anagraphics].[Notifiche_TipologiaNotifiche](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[notifica_id] [int] NOT NULL,
	[tipologia_id] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_Notifiche_TipologiaNotifiche] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[ParametriApplicativi]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Anagraphics].[ParametriApplicativi](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[chiave] [nvarchar](50) NOT NULL,
	[valore] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_ParametriApplicativi] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[Roles]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Anagraphics].[Roles](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[description] [nvarchar](500) NULL,
	[is_admin] [bit] NULL,
	[email] [nvarchar](250) NULL,
	[deleted] [bit] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_Roles] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[roles_voci_menu]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Anagraphics].[roles_voci_menu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[voce_id] [int] NOT NULL,
	[role_id] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_role_voci_menu] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[scadenze_giorni_garanzie]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Anagraphics].[scadenze_giorni_garanzie](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tipologia] [nvarchar](100) NOT NULL,
	[giorni] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_GiorniScadenzeGaranzie] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[TipologiaGaranzia]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Anagraphics].[TipologiaGaranzia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_TipologiaGaranzia] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[Users]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Anagraphics].[Users](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role_id] [int] NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[email] [nvarchar](250) NOT NULL,
	[username] [nvarchar](250) NOT NULL,
	[password] [nvarchar](250) NOT NULL,
	[token] [nvarchar](max) NULL,
	[is_new] [bit] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
	[valid_from] [datetime] NULL,
	[valid_to] [datetime] NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[voci_menu]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Anagraphics].[voci_menu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[path] [nvarchar](100) NULL,
	[title] [nvarchar](50) NOT NULL,
	[icon] [nvarchar](50) NOT NULL,
	[order_number] [int] NOT NULL,
	[css_class] [nvarchar](100) NULL,
	[identifier] [nvarchar](50) NOT NULL,
	[parent_id] [int] NULL,
	[is_dettaglio] [bit] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_voci_menu] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[canali_garanzia]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[canali_garanzia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nome] [nvarchar](50) NOT NULL,
	[template_oggetto_email] [nvarchar](50) NULL,
	[template_testo_email] [nvarchar](50) NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_canali_garanzia] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[deroga_richiesta]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[deroga_richiesta](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[richiesta_id] [int] NOT NULL,
	[workflow_step_id] [int] NOT NULL,
	[deroga] [bit] NULL,
	[note_deroga] [nvarchar](max) NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[incassi]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[incassi](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[insoluto_id] [int] NOT NULL,
	[quota_ricevuta] [decimal](14, 2) NOT NULL,
	[quota_da_incassare] [decimal](14, 2) NOT NULL,
	[data_incasso] [datetime] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_incassi] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[note_richiesta]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[note_richiesta](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[richiesta_id] [int] NOT NULL,
	[workflow_step_id] [int] NOT NULL,
	[note] [nvarchar](max) NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[notifica]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[notifica](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[codice] [nvarchar](10) NULL,
	[step_id] [nvarchar](50) NULL,
	[nome] [nvarchar](500) NOT NULL,
	[messaggio_notifica] [nvarchar](500) NULL,
	[oggetto_email] [nvarchar](250) NULL,
	[testo_email] [nvarchar](max) NULL,
	[url_to_open] [nvarchar](500) NULL,
	[tipologia_id] [nvarchar](50) NOT NULL,
	[esito_id] [int] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[notifica_gruppo]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[notifica_gruppo](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[notifica_id] [int] NOT NULL,
	[gruppo_id] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[notifica_utente]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[notifica_utente](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[notifica_id] [int] NOT NULL,
	[utente_id] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[richiesta_hst]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[richiesta_hst](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[richiesta_id] [int] NOT NULL,
	[cliente_id] [nvarchar](50) NOT NULL,
	[codice_sala] [nvarchar](50) NOT NULL,
	[codice_tir] [nvarchar](50) NOT NULL,
	[workflow_step_id] [int] NOT NULL,
	[stato_id] [int] NULL,
	[stato_sap_id] [int] NULL,
	[data_attivazione] [datetime] NULL,
	[categoria_id] [int] NULL,
	[presenza_societa_collegate] [bit] NULL,
	[societa_collegate] [nvarchar](500) NULL,
	[presenza_ultimo_bilancio] [bit] NULL,
	[anno_bilancio] [int] NULL,
	[presenza_eventi_negativi] [bit] NULL,
	[eventi_negativi] [nvarchar](500) NULL,
	[presenza_esiti_pregressi] [bit] NULL,
	[side_letter] [bit] NULL,
	[canale_contratto_id] [int] NULL,
	[canale_sottoscrizione_id] [int] NULL,
	[deroga_contratto_id] [int] NULL,
	[deroga_merito_user] [nvarchar](50) NULL,
	[deroga_merito_date] [datetime] NULL,
	[deroga_merito_id] [int] NULL,
	[deroga_merito_nota_id] [int] NULL,
	[salvata] [bit] NULL,
	[work_user_id] [int] NULL,
	[work_user] [nvarchar](50) NULL,
	[work_date] [datetime] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
	[ip_address] [nvarchar](50) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[richiesta_timeline]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[richiesta_timeline](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[richiesta_id] [int] NOT NULL,
	[titolo] [nvarchar](500) NULL,
	[messaggio] [nvarchar](500) NOT NULL,
	[icon] [nvarchar](50) NULL,
	[color_class] [nvarchar](50) NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
	[ip_address] [nvarchar](50) NULL,
 CONSTRAINT [PK_richiesta_timeline] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[richiesta_timeline_attachments]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[richiesta_timeline_attachments](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[timeline_id] [int] NOT NULL,
	[documento_id] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_richiesta_timeline_attachments] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[richiesta_timeline_mail]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[richiesta_timeline_mail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[timeline_id] [int] NOT NULL,
	[registro_mail_id] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_richiesta_timeline_mail] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[setup]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[setup](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[descrizione] [nvarchar](250) NOT NULL,
	[valore] [nvarchar](250) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[solleciti]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[solleciti](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[insoluto_id] [int] NOT NULL,
	[tipologia_id] [int] NOT NULL,
	[nome] [nvarchar](50) NOT NULL,
	[stato_id] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_solleciti] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[stato_garanzie]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[stato_garanzie](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[descrizione] [nvarchar](250) NOT NULL,
	[percentuale] [int] NULL,
	[codice_soap] [nvarchar](50) NULL,
	[deroga] [bit] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tipologia_notifica]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipologia_notifica](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nome] [nvarchar](50) NOT NULL,
	[descrizione] [nvarchar](500) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tipologia_solleciti]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipologia_solleciti](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nome] [nvarchar](100) NOT NULL,
	[ordine] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_tipologia_solleciti] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[utenti_gruppi]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[utenti_gruppi](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[utente_id] [int] NOT NULL,
	[gruppo_id] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_utenti_gruppi] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Workflow_navigazione]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Workflow_navigazione](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[workflow_step_id] [int] NOT NULL,
	[workflow_prossimo_step_id] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[workflow_step_roles]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[workflow_step_roles](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role_id] [int] NOT NULL,
	[workflow_step_id] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_workflow_step_roles] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Logs].[application_logs]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Logs].[application_logs](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[type] [nvarchar](20) NULL,
	[area] [nvarchar](100) NULL,
	[method] [nvarchar](150) NULL,
	[message] [nvarchar](max) NOT NULL,
	[stack_trace] [nvarchar](max) NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_application_logs] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [Logs].[HistoryNotifiche]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Logs].[HistoryNotifiche](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[utente_id] [int] NULL,
	[gruppo_id] [int] NULL,
	[notifica_id] [int] NOT NULL,
	[is_new] [bit] NOT NULL,
	[message] [nvarchar](500) NULL,
	[url_to_open] [nvarchar](500) NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_history_notifiche] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Logs].[registro_email]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Logs].[registro_email](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[mittente] [nvarchar](500) NOT NULL,
	[destinatari] [nvarchar](500) NOT NULL,
	[oggetto] [nvarchar](500) NOT NULL,
	[testo] [nvarchar](max) NOT NULL,
	[tipo] [nvarchar](20) NOT NULL,
	[richiesta_id] [int] NULL,
	[workflow_step_id] [int] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_registro_email] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [Logs].[registro_notifiche]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Logs].[registro_notifiche](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[richiesta_id] [int] NOT NULL,
	[codice_notifica] [nvarchar](10) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_registro_notifiche] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Statistics].[ClientiAttivi]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Statistics].[ClientiAttivi](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[anno] [int] NOT NULL,
	[mese] [int] NOT NULL,
	[business] [nvarchar](10) NULL,
	[clienti_attivi] [int] NOT NULL,
	[giorno_aggiornamento] [int] NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_ClientiAttivi] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Statistics].[NuoviProspect]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Statistics].[NuoviProspect](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[anno] [int] NOT NULL,
	[settimana] [int] NOT NULL,
	[business] [nvarchar](10) NULL,
	[lunedi] [int] NULL,
	[martedi] [int] NULL,
	[mercoledi] [int] NULL,
	[giovedi] [int] NULL,
	[venerdi] [int] NULL,
	[sabato] [int] NULL,
	[domenica] [int] NULL,
	[totale] [int] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL,
 CONSTRAINT [PK_NuoviProspect] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [Anagraphics].[Users] ADD  CONSTRAINT [DF_Users_is_new]  DEFAULT ((1)) FOR [is_new]
GO
ALTER TABLE [Logs].[HistoryNotifiche] ADD  CONSTRAINT [DF_history_notifiche_is_new]  DEFAULT ((1)) FOR [is_new]
GO
ALTER TABLE [Anagraphics].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_Roles] FOREIGN KEY([role_id])
REFERENCES [Anagraphics].[Roles] ([id])
GO
ALTER TABLE [Anagraphics].[Users] CHECK CONSTRAINT [FK_Users_Roles]
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateProspectDocument]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 28/01/2022
-- Description:	Inserisce / Aggiorna un nuovo documento relativo ad una richiesta
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_CreateProspectDocument]
	@document_id INT = -1,
	@richiesta_id INT,
	@tipo_doc_id INT,
	@doc_name NVARCHAR(500),
	@doc_path NVARCHAR(2500),
	@doc_blob VARBINARY(MAX),
	@doc_type NVARCHAR(50),
	@doc_ext NVARCHAR(10),
	@user NVARCHAR(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();
	DECLARE @return_id INT = -1;

	IF(@document_id > 0)
		BEGIN
			UPDATE [dbo].[documenti_cliente]
			   SET [richiesta_id] = @richiesta_id
				  ,[tipo_doc_id] = @tipo_doc_id
				  ,[doc_name] = @doc_name
				  ,[doc_path] = @doc_path
				  ,[doc_blob] = @doc_blob
				  ,[doc_type] = @doc_type
				  ,[doc_ext] = @doc_ext
				  ,[last_mod_user] = @user
				  ,[last_mod_date] = @DataAttuale
			 WHERE [id] = @document_id

					   
			SET @return_id = @document_id;
		END
	ELSE
		BEGIN
			INSERT INTO [dbo].[documenti_cliente]
					   ([richiesta_id]
					   ,[tipo_doc_id]
					   ,[doc_name]
					   ,[doc_path]
					   ,[doc_blob]
					   ,[doc_type]
					   ,[doc_ext]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@richiesta_id
					   ,@tipo_doc_id
					   ,@doc_name
					   ,@doc_path
					   ,@doc_blob
					   ,@doc_type
					   ,@doc_ext
					   ,@user
					   ,@DataAttuale)
					   
			SET @return_id = SCOPE_IDENTITY();
		END

		SELECT [return_id] = @return_id
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateProspectFromImport]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 16/12/2021
-- Description:	Crea un nuovo prospect nel database
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_CreateProspectFromImport]
	@CodiceCliente nvarchar(50),
	@NomeCliente nvarchar(500),
	@CodiceTIR nvarchar(50),
	@CodiceSala nvarchar(50),
	@Business nvarchar(500),
	@ReferenteArea nvarchar(500),
	@UtenteOperazione nvarchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
					
	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @IdRichiesta INT = -1,
			@IdCliente INT = ISNULL((
									SELECT [id]
									  FROM [dbo].[cliente]
									 WHERE [codice_cliente] = @CodiceCliente
									   AND [business] = @Business
									),-1);
	DECLARE @IdBusiness INT = ISNULL((
									SELECT [id]
									  FROM [Anagraphics].[Business]
									 WHERE [name] = @Business
									),-1);


	IF (@IdBusiness = -1)
		BEGIN
			INSERT INTO [Anagraphics].[Business]
					   ([name]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@Business
					   ,@UtenteOperazione
					   ,@DataAttuale)

			SET @IdBusiness = SCOPE_IDENTITY();
		END

	IF (@IdCliente = -1)
		BEGIN
			INSERT INTO [dbo].[cliente]
					   ([codice_cliente]
					   ,[nome_cliente]
					   ,[business]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@CodiceCliente
					   ,@NomeCliente
					   ,@Business
					   ,@UtenteOperazione
					   ,@DataAttuale)

			SET @IdCliente = SCOPE_IDENTITY();
		END
	ELSE
		BEGIN
			UPDATE [dbo].[cliente]
			   SET [nome_cliente] = @NomeCliente
				  ,[last_mod_user] = @UtenteOperazione
				  ,[last_mod_date] = @DataAttuale
			 WHERE [id] = @IdCliente
		END

	IF NOT EXISTS(
				SELECT [id]
				  FROM [Anagraphics].[Cliente_Business]
				 WHERE [cliente_id] = @IdCliente
				   AND [business_id] = @IdBusiness
				)
		BEGIN

			INSERT INTO [Anagraphics].[Cliente_Business]
					   ([cliente_id]
					   ,[business_id]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@IdCliente
					   ,@IdBusiness
					   ,@UtenteOperazione
					   ,@DataAttuale)
		END

		IF(@IdCliente > 0)
		BEGIN
			DECLARE @NextStepId INT = -1,
					@NextStepIndex INT = -1;

			SELECT TOP(1) 
						@NextStepId = ISNULL([Id],-1),
						@NextStepIndex = ISNULL([tab_index],-1)
					FROM [Procedural].[FC_GetDefaultWorkflowStep](@Business)

			INSERT INTO [dbo].[richiesta]
					   ([cliente_id]
					   ,[stato_id]
					   ,[codice_sala]
					   ,[codice_tir]
					   ,[workflow_step_id]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@IdCliente
					   ,NULL
					   ,@CodiceSala
					   ,@CodiceTIR
					   ,@NextStepId
					   ,@UtenteOperazione
					   ,@DataAttuale)

			SET @IdRichiesta = SCOPE_IDENTITY();
		END

		SELECT [return_id] = @IdRichiesta
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateProspectGaranzia]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 04/02/2022
-- Description:	Inserisce o aggiorna una garanzia
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_CreateProspectGaranzia]
	@IdRichiesta INT,
	@CodiceGaranzia NVARCHAR(50),
	@StatoSOAP NVARCHAR(50),
	@TipologiaSOAP NVARCHAR(50),
	@Prestato DECIMAL(14,2),
	@Dovuto DECIMAL(14,2),
	@NoteDeroga NVARCHAR(MAX),
	@ProtocolloSOAP NVARCHAR(50) = '',
	@DirittoSOAP NVARCHAR(50) = '',
	@UtenteOperazione nvarchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
					
	DECLARE @DataAttuale DATETIME = GETDATE();
	DECLARE @IdTipologia INT = NULL;
	DECLARE @IsCreate BIT = 0;

	IF(@TipologiaSOAP != '')
	BEGIN
		SET @IdTipologia = (
								SELECT [id]
								  FROM [Anagraphics].[TipologiaGaranzia]
								 WHERE [name] = @TipologiaSOAP
						);

		IF(@IdTipologia IS NULL)
		BEGIN
			INSERT INTO [Anagraphics].[TipologiaGaranzia]
					   ([name]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@TipologiaSOAP
					   ,@UtenteOperazione
					   ,@DataAttuale)

			SET @IdTipologia = SCOPE_IDENTITY();
		END
	END

	DECLARE @IdStato INT = NULL,
			@Deroga BIT = 0;	
	
	SELECT @IdStato = [id],
		   @Deroga = ISNULL([deroga],0)
	  FROM [dbo].[stato_garanzie]
	 WHERE [codice_soap] = @StatoSOAP

	DECLARE @IdGaranzia INT = ISNULL((
									SELECT [id]
									  FROM [dbo].[garanzie]
									 WHERE [richiesta_id] = @IdRichiesta
									   AND [codice_garanzia] = @CodiceGaranzia
									),-1);

	IF (@IdGaranzia = -1)
		BEGIN
			INSERT INTO [dbo].[garanzie]
					   ([richiesta_id]
					   ,[codice_garanzia]
					   ,[stato_id]
					   ,[tipologia_id]
					   ,[deroga]
					   ,[note_deroga]
					   ,[prestato]
					   ,[dovuto]
					   ,[protocollo]
					   ,[diritto]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@IdRichiesta
					   ,@CodiceGaranzia
					   ,@IdStato
					   ,@IdTipologia
					   ,@Deroga
					   ,@NoteDeroga
					   ,CASE WHEN @Prestato IS NULL THEN 0 ELSE @Prestato END
					   ,CASE WHEN @Dovuto IS NULL THEN 0 ELSE @Dovuto END
					   ,@ProtocolloSOAP
					   ,@DirittoSOAP
					   ,@UtenteOperazione
					   ,@DataAttuale)

			SET @IdGaranzia = SCOPE_IDENTITY();
			SET @IsCreate = 1;
		END
	ELSE
		BEGIN
			UPDATE [dbo].[garanzie]
			   SET [stato_id] = @IdStato
				  ,[tipologia_id] = @IdTipologia
				  ,[deroga] = @Deroga
				  ,[note_deroga] = @NoteDeroga
				  ,[prestato] = CASE WHEN @Prestato IS NULL THEN [prestato] ELSE @Prestato END
				  ,[dovuto] = CASE WHEN @Dovuto IS NULL THEN [dovuto] ELSE @Dovuto END
				  ,[protocollo] = @ProtocolloSOAP
				  ,[diritto] = @DirittoSOAP
				  ,[last_mod_user] = @UtenteOperazione
				  ,[last_mod_date] = @DataAttuale
			 WHERE [richiesta_id] = @IdRichiesta
			   AND [codice_garanzia] = @CodiceGaranzia

			SET @IsCreate = 0;
		END

		SELECT [idResponse] = @IdGaranzia,
			   [isCreate] = @IsCreate
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateUpdateRegistroNotifiche]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 11/04/2022
-- Description:	Crea o modifica un record nella tabella registro_notifiche
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_CreateUpdateRegistroNotifiche]
	@richiestaId INT,
	@codiceNotifica NVARCHAR(10) = '',
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @id INT = ISNULL((
							SELECT TOP(1) [id]
							  FROM [Logs].[registro_notifiche]
							 WHERE [richiesta_id] = @richiestaId
							   AND [codice_notifica] = @codiceNotifica
						),-1);

    IF(@id = -1)
		BEGIN

			INSERT INTO [Logs].[registro_notifiche]
					   ([richiesta_id]
					   ,[codice_notifica]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@richiestaId
					   ,@codiceNotifica
					   ,@utenteUpdate
					   ,@DataAttuale)
					   
			SET @id = SCOPE_IDENTITY();
		END

	SELECT [return_id] = @id
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateUpdateRichiestaTimelineMail]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 02/05/2022
-- Description:	Crea o modifica un record nella tabella RichiestaTimelineMail
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_CreateUpdateRichiestaTimelineMail]
	@idTimeline INT = -1,
	@idRegistroMail INT = -1,
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE(),
			@id INT = -1;

	DECLARE @IdExt INT = ISNULL((
								SELECT [id]
								  FROM [dbo].[richiesta_timeline_mail]
								 WHERE [timeline_id] = @idTimeline
								   AND [registro_mail_id] = @idRegistroMail
								),-1);

    IF (@IdExt > 0)
		BEGIN
			UPDATE [dbo].[richiesta_timeline_mail]
			   SET [last_mod_user] = @utenteUpdate
				  ,[last_mod_date] = @DataAttuale
			 WHERE [id] = @IdExt

			 SET @id = @IdExt;
		END
	ELSE
		BEGIN
			INSERT INTO [dbo].[richiesta_timeline_mail]
					   ([timeline_id]
					   ,[registro_mail_id]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@idTimeline
					   ,@idRegistroMail
					   ,@utenteUpdate
					   ,@DataAttuale)
					   
			SET @id = SCOPE_IDENTITY();
		END

	SELECT [return_id] = @id
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateUpdateRole]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 05/02/2022
-- Description:	Crea o modifica un ruolo
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_CreateUpdateRole]
	@id INT = -1,
	@name NVARCHAR(50) = '',
	@isAdmin BIT = 0,
	@email NVARCHAR(250) = '',
	@description NVARCHAR(500) = '',
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

    IF(@id > 0)
		BEGIN
			UPDATE [Anagraphics].[Roles]
			   SET [name] = @name
				  ,[description] = @description
				  ,[is_admin] = @isAdmin
				  ,[email] = @email
				  ,[last_mod_user] = @utenteUpdate
				  ,[last_mod_date] = @DataAttuale
			 WHERE [id] = @id
		END
	ELSE
		BEGIN

			INSERT INTO [Anagraphics].[Roles]
					   ([name]
					   ,[description]
					   ,[is_admin]
					   ,[email]
					   ,[deleted]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@name
					   ,@description
					   ,@isAdmin
					   ,@email
					   ,0
					   ,@utenteUpdate
					   ,@DataAttuale)
					   
			SET @id = SCOPE_IDENTITY();
		END

	SELECT [return_id] = @id
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateUpdateRoleVociMenu]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 30/03/2022
-- Description:	Crea o modifica un [Anagraphics].[roles_voci_menu]
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_CreateUpdateRoleVociMenu]
	@id INT = -1,
	@roleId INT = -1,
	@voceId INT = -1,
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @IdExt INT = ISNULL((
								SELECT [id]
								  FROM [Anagraphics].[roles_voci_menu]
								 WHERE [role_id] = @roleId
								   AND [voce_id] = @voceId
								),-1);
	IF(@IdExt = -1 OR @id = @IdExt)
		BEGIN
			IF(@id > 0)
				BEGIN

					UPDATE [Anagraphics].[roles_voci_menu]
					   SET [role_id] = @roleId
						  ,[voce_id] = @voceId
						  ,[last_mod_user] = @utenteUpdate
						  ,[last_mod_date] = @DataAttuale
					 WHERE [id] = @id
				END
			ELSE
				BEGIN

					INSERT INTO [Anagraphics].[roles_voci_menu]
							   ([role_id]
							   ,[voce_id]
							   ,[create_user]
							   ,[create_date])
						 VALUES
							   (@roleId
							   ,@voceId
							   ,@utenteUpdate
							   ,@DataAttuale)
					   
					SET @id = SCOPE_IDENTITY();
				END
				
			SELECT [return_id] = @id
		END
	ELSE
		BEGIN
			SELECT [return_id] = -2
		END
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateUpdateSalaRichiesta]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 04/02/2022
-- Description:	Inserisce o aggiorna una sala
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_CreateUpdateSalaRichiesta]
	@IdRichiesta INT,
	@CodiceSala NVARCHAR(50),
	@NumeroMacchine INT,
	@UtenteOperazione nvarchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
					
	DECLARE @DataAttuale DATETIME = GETDATE();
	DECLARE @IsCreate BIT = 0;

	DECLARE @IdSala INT = ISNULL((
									SELECT [id]
									  FROM [dbo].[sale_richiesta]
									 WHERE [richiesta_id] = @IdRichiesta
									   AND [codice_sala] = @CodiceSala
									),-1);

	IF (@IdSala = -1)
		BEGIN

			INSERT INTO [dbo].[sale_richiesta]
					   ([richiesta_id]
					   ,[codice_sala]
					   ,[numero_macchine]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@IdRichiesta
					   ,@CodiceSala
					   ,@NumeroMacchine
					   ,@UtenteOperazione
					   ,@DataAttuale)

			SET @IdSala = SCOPE_IDENTITY();
			SET @IsCreate = 1;
		END
	ELSE
		BEGIN

			UPDATE [dbo].[sale_richiesta]
			   SET [numero_macchine] = @NumeroMacchine
				  ,[last_mod_user] = @UtenteOperazione
				  ,[last_mod_date] = @DataAttuale
			 WHERE [richiesta_id] = @IdRichiesta
			   AND [codice_sala] = @CodiceSala

			SET @IsCreate = 0;
		END

		SELECT [idResponse] = @IdSala,
			   [isCreate] = @IsCreate
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateUpdateScadenzeGiorniGaranzie]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 13/04/2022
-- Description:	Crea o modifica un [Anagraphics].[scadenze_giorni_garanzie]
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_CreateUpdateScadenzeGiorniGaranzie]
	@id INT = -1,
	@tipologia NVARCHAR(100),
	@giorni INT = 0,
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @IdExt INT = ISNULL((
								SELECT [id]
								  FROM [Anagraphics].[scadenze_giorni_garanzie]
								 WHERE [tipologia] = @tipologia
								),-1);
	IF(@IdExt = -1 OR @id = @IdExt)
		BEGIN
			IF(@id > 0)
				BEGIN

					UPDATE [Anagraphics].[scadenze_giorni_garanzie]
					   SET [tipologia] = @tipologia
						  ,[giorni] = @giorni
						  ,[last_mod_user] = @utenteUpdate
						  ,[last_mod_date] = @DataAttuale
					 WHERE [id] = @id
				END
			ELSE
				BEGIN

					INSERT INTO [Anagraphics].[scadenze_giorni_garanzie]
							   ([tipologia]
							   ,[giorni]
							   ,[create_user]
							   ,[create_date])
						 VALUES
							   (@tipologia
							   ,@giorni
							   ,@utenteUpdate
							   ,@DataAttuale)
					   
					SET @id = SCOPE_IDENTITY();
				END
				
			SELECT [return_id] = @id
		END
	ELSE
		BEGIN
			SELECT [return_id] = -2
		END
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateUpdateTirSala]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 04/02/2022
-- Description:	Inserisce o aggiorna una sala
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_CreateUpdateTirSala]
	@IdSala INT,
	@CodiceTir NVARCHAR(50),
	@UtenteOperazione nvarchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
					
	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @IdTir INT = ISNULL((
									SELECT [id]
									  FROM [dbo].[tir_sala]
									 WHERE [sala_id] = @IdSala
									   AND [codice_tir] = @CodiceTir
									),-1);

	IF (@IdTir = -1)
		BEGIN

			INSERT INTO [dbo].[tir_sala]
					   ([sala_id]
					   ,[codice_tir]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@IdSala
					   ,@CodiceTir
					   ,@UtenteOperazione
					   ,@DataAttuale)

			SET @IdTir = SCOPE_IDENTITY();
		END
	ELSE
		BEGIN


			UPDATE [dbo].[tir_sala]
			   SET [last_mod_user] = @UtenteOperazione
				  ,[last_mod_date] = @DataAttuale
			 WHERE [sala_id] = @IdSala
			   AND [codice_tir] = @CodiceTir

		END

		SELECT [return_id] = @IdTir
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateUpdateUser]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 01/02/2022
-- Description:	Crea o modifica un utente
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_CreateUpdateUser]
	@id INT = -1,
	@role_id INT,
	@name NVARCHAR(50) = '',
	@email NVARCHAR(250) = '',
	@username NVARCHAR(250) = '',
	@password NVARCHAR(250) = '',
	@valid_from DATETIME,
	@valid_to DATETIME,
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

    IF(@id > 0)
		BEGIN
			UPDATE [Anagraphics].[Users]
			   SET [role_id] = @role_id
				  ,[name] = @name
				  ,[email] = @email
				  ,[username] = @username
				  ,[password] = CASE WHEN @password != '' THEN @password ELSE [password] END
				  ,[last_mod_user] = @utenteUpdate
				  ,[last_mod_date] = @DataAttuale
				  ,[valid_from] = @valid_from
				  ,[valid_to] = @valid_to
			 WHERE [id] = @id
		END
	ELSE
		BEGIN
			INSERT INTO [Anagraphics].[Users]
					   ([role_id]
					   ,[name]
					   ,[email]
					   ,[username]
					   ,[password]
					   ,[token]
					   ,[is_new]
					   ,[create_user]
					   ,[create_date]
					   ,[valid_from]
					   ,[valid_to])
				 VALUES
					   (@role_id
					   ,@name
					   ,@email
					   ,@username
					   ,@password
					   ,''
					   ,1
					   ,@utenteUpdate
					   ,@DataAttuale
					   ,@valid_from
					   ,@valid_to)
					   
			SET @id = SCOPE_IDENTITY();
		END

		SELECT [return_id] = @id
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateUpdateWorkflowStepRole]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 30/03/2022
-- Description:	Crea o modifica un workflow_step_roles
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_CreateUpdateWorkflowStepRole]
	@id INT = -1,
	@roleId INT = -1,
	@workflowStepId INT = -1,
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @IdExt INT = ISNULL((
								SELECT [id]
								  FROM [dbo].[workflow_step_roles]
								 WHERE [role_id] = @roleId
								   AND [workflow_step_id] = @workflowStepId
								),-1);
	IF(@IdExt = -1 OR @id = @IdExt)
		BEGIN
			IF(@id > 0)
				BEGIN

					UPDATE [dbo].[workflow_step_roles]
					   SET [role_id] = @roleId
						  ,[workflow_step_id] = @workflowStepId
						  ,[last_mod_user] = @utenteUpdate
						  ,[last_mod_date] = @DataAttuale
					 WHERE [id] = @id
				END
			ELSE
				BEGIN

					INSERT INTO [dbo].[workflow_step_roles]
							   ([role_id]
							   ,[workflow_step_id]
							   ,[create_user]
							   ,[create_date])
						 VALUES
							   (@roleId
							   ,@workflowStepId
							   ,@utenteUpdate
							   ,@DataAttuale)
					   
					SET @id = SCOPE_IDENTITY();
				END
				
			SELECT [return_id] = @id
		END
	ELSE
		BEGIN
			SELECT [return_id] = -2
		END
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_DeleteRole]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 05/02/2022
-- Description:	Rimuove un ruolo
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_DeleteRole]
	@IdRuolo INT,
	@UtenteOperazione NVARCHAR(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	UPDATE [Anagraphics].[Roles]
	   SET [last_mod_user] = @UtenteOperazione
		  ,[last_mod_date] = GETDATE()
		  ,[deleted] = 1
	 WHERE [id] = @IdRuolo
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_DeleteRoleVociMenu]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 30/03/2022
-- Description:	Rimuove un record dalla tabella [Anagraphics].[roles_voci_menu]
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_DeleteRoleVociMenu]
	@roleVoceId INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DELETE FROM [Anagraphics].[roles_voci_menu]
		  WHERE [id] = @roleVoceId
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_DeleteUser]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 04/02/2022
-- Description:	Rimuove un utente
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_DeleteUser]
	@IdUtente INT,
	@UtenteOperazione NVARCHAR(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataDelete DATETIME = DATEADD (day , -1 , GETDATE()); 

	UPDATE [Anagraphics].[Users]
	   SET [last_mod_user] = @UtenteOperazione
		  ,[last_mod_date] = GETDATE()
		  ,[valid_to] = @DataDelete
	 WHERE [id] = @IdUtente
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_DeleteWorkflowStepRole]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 30/03/2022
-- Description:	Rimuove un record dalla tabella
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_DeleteWorkflowStepRole]
	@workflowStepRoleId INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DELETE FROM [dbo].[workflow_step_roles]
		  WHERE [id] = @workflowStepRoleId
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_GetAvaiableBusinessByUser]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 04/04/2022
-- Description:	Recupera la lista dei business abilitati in base all'id utente
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_GetAvaiableBusinessByUser]
	@idUser INT,
	@isAdmin BIT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	IF(@isAdmin = 1)
		BEGIN
			SELECT [name]
			  FROM [Anagraphics].[Business]
		END
	ELSE
		BEGIN
			SELECT DISTINCT [W].[business]
			  FROM [dbo].[workflow_step_roles] [W_S_R]
			  JOIN [dbo].[Workflow_step] [W_S] ON [W_S_R].[workflow_step_id] = [W_S].[id]
			  JOIN [dbo].[Workflow] [W] ON [W].[id] = [W_S].[workflow_id]
			  JOIN [Anagraphics].[Users] [U] ON [U].[role_id] = [W_S_R].[role_id]
			 WHERE [U].[id] = @idUser
		END
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_GetUserByUsername]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 09/12/2021
-- Description:	Carica un utente
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_GetUserByUsername]
	@username nvarchar(250)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	SELECT [Users].[id]
		  ,[Users].[role_id]
		  ,ISNULL([Roles].[name],'') AS [role_name]
		  ,[Users].[name]
		  ,[Users].[email]
		  ,[Users].[username]
		  ,[Users].[password]
		  ,[Users].[token]
		  ,[Users].[is_new]
	  FROM
		[Anagraphics].[Users] AS [Users]
 LEFT JOIN
		[Anagraphics].[Roles] ON [role_id] = [Roles].[id]
	 WHERE
		[username] = @username
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_LogoutUser]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 04/02/2022
-- Description:	Rimuove il token da un utente
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_LogoutUser]
	@IdUtente INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	UPDATE [Anagraphics].[Users]
	   SET [token] = ''
	 WHERE [id] = @IdUtente
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_UpdateAttivaCliente]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 11/02/2022
-- Description:	Attiva un cliente
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_UpdateAttivaCliente]
	@idRichiesta INT,
	@utenteUpdate NVARCHAR(50),
	@parametroApplicativo NVARCHAR(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    UPDATE [dbo].[richiesta]
   SET [stato_sap_id] = ISNULL((SELECT TOP (1) [id]
								  FROM [PortaleCredito].[dbo].[stato_richiesta]
								 WHERE [nome] = ISNULL((
														SELECT [valore]
														  FROM [Anagraphics].[ParametriApplicativi]
														 WHERE [chiave] = @parametroApplicativo --'statoAttivoSAP'
								),'')
							),[stato_sap_id])
      ,[data_attivazione] = GETDATE()
      ,[last_mod_user] = @utenteUpdate
      ,[last_mod_date] = GETDATE()
 WHERE [id] = @idRichiesta
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_UpdateProspectGaranzia]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 07/02/2022
-- Description:	Salva lo stato di una garaniza
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_UpdateProspectGaranzia]
	@Id INT,
	@IdRichiesta INT,
	@CodiceGaranzia NVARCHAR(50),
	@StatoId INT,
	@ValidationId UNIQUEIDENTIFIER,
	@Deroga BIT,
	@NoteDeroga NVARCHAR(MAX) = '',
	@UtenteOperazione NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

	IF(@Id = -1)
	BEGIN
		SET @Id = ISNULL((
							SELECT TOP(1) [id]
							  FROM [dbo].[garanzie]
							 WHERE [codice_garanzia] = @CodiceGaranzia
							   AND [richiesta_id] = @IdRichiesta
						),-1);
	END

	UPDATE [dbo].[garanzie]
	   SET [richiesta_id] = @IdRichiesta
		  ,[codice_garanzia] = @CodiceGaranzia
		  ,[stato_id] = @StatoId
		  ,[validation_id] = @ValidationId
		  ,[deroga] = @Deroga
		  ,[note_deroga] = @NoteDeroga
		  ,[last_mod_user] = @UtenteOperazione
		  ,[last_mod_date] = @DataAttuale
	 WHERE [id] = @Id

		SELECT [return_id] = @Id
END
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_UpdateUserPassword]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 14/04/2022
-- Description:	Modifica la password di un utente se la vecchia password è corretta
-- =============================================
CREATE PROCEDURE [Anagraphics].[SP_UpdateUserPassword]
	@idUser INT,
	@passwordPrecedente NVARCHAR(250),
	@passwordNuova NVARCHAR(250),
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();
	DECLARE @return_id INT = -1;

	IF EXISTS(
				SELECT TOP(1) [id]
				  FROM [Anagraphics].[Users]
				 WHERE [id] = @idUser
				   AND [password] = @passwordPrecedente
			)
		BEGIN
			UPDATE [Anagraphics].[Users]
			   SET [password] = @passwordNuova
				  ,[last_mod_user] = @utenteUpdate
				  ,[last_mod_date] = @DataAttuale
				  ,[is_new] = 0
			 WHERE [id] = @idUser
			   AND [password] = @passwordPrecedente

			SET @return_id = 1;
		END
	ELSE
		BEGIN
			SET @return_id = -2;
		END

	SELECT [return_id] = @return_id
END
GO
/****** Object:  StoredProcedure [dbo].[SP_ATTENZIONE_DELETE_DB]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 17/02/2022
-- Description:	Pulisce il database
-- =============================================
CREATE PROCEDURE [dbo].[SP_ATTENZIONE_DELETE_DB]
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	-- TABELLE DI COLLEGAMENTO

	DBCC CHECKIDENT ('[Anagraphics].[Cliente_Business]', RESEED, 0);  
    DELETE FROM [Anagraphics].[Cliente_Business]
	
	DBCC CHECKIDENT ('[dbo].[deroga_richiesta]', RESEED, 0);  
	DELETE FROM [dbo].[deroga_richiesta]
	
	DBCC CHECKIDENT ('[dbo].[documenti_cliente]', RESEED, 0);  
	DELETE FROM [dbo].[documenti_cliente]
	
	DBCC CHECKIDENT ('[dbo].[note_richiesta]', RESEED, 0);  
	DELETE FROM [dbo].[note_richiesta]
	
	DBCC CHECKIDENT ('[dbo].[tir_sala]', RESEED, 0);  
	DELETE FROM [dbo].[tir_sala]
	
	DBCC CHECKIDENT ('[dbo].[sale_richiesta]', RESEED, 0);  
	DELETE FROM [dbo].[sale_richiesta]

	-- TIMELINE
	
	DBCC CHECKIDENT ('[dbo].[richiesta_timeline_attachments]', RESEED, 0);  
	DELETE FROM [dbo].[richiesta_timeline_attachments]
	
	DBCC CHECKIDENT ('[dbo].[richiesta_timeline]', RESEED, 0);  
	DELETE FROM [dbo].[richiesta_timeline]

	-- STATISTICHE
	
	DBCC CHECKIDENT ('[Statistics].[ClientiAttivi]', RESEED, 0);  
	DELETE FROM [Statistics].[ClientiAttivi]
	
	DBCC CHECKIDENT ('[Statistics].[NuoviProspect]', RESEED, 0);  
	DELETE FROM [Statistics].[NuoviProspect]
	
	DBCC CHECKIDENT ('[Statistics].[StatoScadenzaGaranzia]', RESEED, 0);  
	DELETE FROM [Statistics].[StatoScadenzaGaranzia]

	-- TABELLE PRINCIPALI
	
	DBCC CHECKIDENT ('[dbo].[richiesta]', RESEED, 0);  
	DELETE FROM [dbo].[richiesta]
	
	DBCC CHECKIDENT ('[dbo].[richiesta_hst]', RESEED, 0);  
	DELETE FROM [dbo].[richiesta_hst]
	
	DBCC CHECKIDENT ('[dbo].[cliente]', RESEED, 0);  
	DELETE FROM [dbo].[cliente]
	
	DBCC CHECKIDENT ('[dbo].[garanzie]', RESEED, 0);  
	DELETE FROM [dbo].[garanzie]

	-- NOTIFICHE
	
	DBCC CHECKIDENT ('[Logs].[application_logs]', RESEED, 0);  
	DELETE FROM [Logs].[application_logs]
	
	DBCC CHECKIDENT ('[Logs].[HistoryNotifiche]', RESEED, 0);  
	DELETE FROM [Logs].[HistoryNotifiche]
	
	DBCC CHECKIDENT ('[Logs].[registro_email]', RESEED, 0);  
	DELETE FROM [Logs].[registro_email]
END
GO
/****** Object:  StoredProcedure [dbo].[SP_DeleteRichiestaById]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 17/02/2022
-- Description:	Cancella una richiesta
-- =============================================
CREATE PROCEDURE [dbo].[SP_DeleteRichiestaById]
	@IdRichiesta INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	-- TABELLE DI COLLEGAMENTO
	
	DELETE FROM [dbo].[deroga_richiesta] WHERE [richiesta_id] = @IdRichiesta
	
	DELETE FROM [dbo].[documenti_cliente] WHERE [richiesta_id] = @IdRichiesta
	
	DELETE FROM [dbo].[note_richiesta] WHERE [richiesta_id] = @IdRichiesta

	DELETE FROM [dbo].[tir_sala] WHERE [sala_id] IN (SELECT [id] FROM [dbo].[sale_richiesta] WHERE [richiesta_id] = @IdRichiesta)
	
	DELETE FROM [dbo].[sale_richiesta] WHERE [richiesta_id] = @IdRichiesta

	-- TIMELINE
	
	DELETE FROM [dbo].[richiesta_timeline_attachments] WHERE [timeline_id] IN (SELECT [id] FROM [dbo].[richiesta_timeline] WHERE [richiesta_id] = @IdRichiesta)
	
	DELETE FROM [dbo].[richiesta_timeline] WHERE [richiesta_id] = @IdRichiesta

	-- TABELLE PRINCIPALI
	 
	DELETE FROM [dbo].[richiesta] WHERE [id] = @IdRichiesta
	
	DELETE FROM [dbo].[richiesta_hst] WHERE [richiesta_id] = @IdRichiesta
	
	DELETE FROM [dbo].[garanzie] WHERE [richiesta_id] = @IdRichiesta

END
GO
/****** Object:  StoredProcedure [dbo].[SP_DeleteSaleTirByRichiestaId]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 17/02/2022
-- Description:	Cancella sale e tir di una richiesta
-- =============================================
CREATE PROCEDURE [dbo].[SP_DeleteSaleTirByRichiestaId]
	@IdRichiesta INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	-- TABELLE DI COLLEGAMENTO

	DELETE FROM [dbo].[tir_sala] WHERE [sala_id] IN (SELECT [id] FROM [dbo].[sale_richiesta] WHERE [richiesta_id] = @IdRichiesta)
	
	DELETE FROM [dbo].[sale_richiesta] WHERE [richiesta_id] = @IdRichiesta

END
GO
/****** Object:  StoredProcedure [dbo].[SP_GetDashboardBottomClientiByRole]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 06/04/2022
-- Description:	Carica gli ultimi 10 prospect
-- =============================================
CREATE PROCEDURE [dbo].[SP_GetDashboardBottomClientiByRole]
	@idRole INT,
	@isAdmin BIT,
	@selectedBusiness NVARCHAR(100) = ''''''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @sql NVARCHAR(MAX) = '
    SELECT TOP(10) *
      FROM [dbo].[VW_ALL_Clienti]
	  WHERE (@isAdmin = 1 OR [business] IN (
					SELECT DISTINCT [W].[business]
					  FROM [dbo].[workflow_step_roles] [W_S_R]
					  JOIN [dbo].[Workflow_step] [W_S] ON [W_S_R].[workflow_step_id] = [W_S].[id]
					  JOIN [dbo].[Workflow] [W] ON [W].[id] = [W_S].[workflow_id]
					 WHERE [role_id] = @idRole
			)
		)
		AND [business] IN (' + @selectedBusiness + ') 
		ORDER BY [last_mod] DESC';

		  --SELECT @sql

	DECLARE @params NVARCHAR(4000) = '@idRole INT, @isAdmin BIT';

	EXEC sp_executesql @sql, @params, @idRole = @idRole, @isAdmin = @isAdmin
END
GO
/****** Object:  StoredProcedure [dbo].[SP_GetDashboardBottomProspectByRole]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 06/04/2022
-- Description:	Carica gli ultimi 10 prospect
-- =============================================
CREATE PROCEDURE [dbo].[SP_GetDashboardBottomProspectByRole]
	@idRole INT,
	@isAdmin BIT,
	@selectedBusiness NVARCHAR(100) = ''''''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @sql NVARCHAR(MAX) = '
    SELECT TOP(10) *
      FROM [dbo].[VW_ALL_Prospect]
	  WHERE (@isAdmin = 1 OR [business] IN (
					SELECT DISTINCT [W].[business]
					  FROM [dbo].[workflow_step_roles] [W_S_R]
					  JOIN [dbo].[Workflow_step] [W_S] ON [W_S_R].[workflow_step_id] = [W_S].[id]
					  JOIN [dbo].[Workflow] [W] ON [W].[id] = [W_S].[workflow_id]
					 WHERE [role_id] = @idRole
			)
		)
		AND [business] IN (' + @selectedBusiness + ') 
		ORDER BY [last_mod] DESC';

		  --SELECT @sql

	DECLARE @params NVARCHAR(4000) = '@idRole INT, @isAdmin BIT';

	EXEC sp_executesql @sql, @params, @idRole = @idRole, @isAdmin = @isAdmin
END
GO
/****** Object:  StoredProcedure [dbo].[SP_GetRichiesteOltreSLA]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 28/03/2022
-- Description:	Restituisce l'elenco dei clienti che dopo un determinato SLA non si trovano in uno stato
-- =============================================
CREATE PROCEDURE [dbo].[SP_GetRichiesteOltreSLA]
	@dayGap INT,
	@workflowIndex INT,
	@statoClienti NVARCHAR(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    SELECT [R].*
	  FROM [dbo].[richiesta] [R]
	  JOIN [dbo].[Workflow_step] [W_S] ON [W_S].[id] = [R].[workflow_step_id]
 LEFT JOIN [dbo].[stato_richiesta] [S_R] ON [S_R].[id] = [R].[stato_id]
	 WHERE DATEDIFF(MINUTE, [R].[create_date], GETDATE())>((@dayGap*24*60))
	   AND [W_S].[tab_index] <= @workflowIndex
	   AND UPPER(ISNULL([S_R].[nome],'')) != @statoClienti
	   AND NOT EXISTS (
						-- Verifico di non averla già inviata
						SELECT TOP(1) [R_N].[id]
						  FROM [Logs].[registro_notifiche] [R_N]
						 WHERE [R_N].[richiesta_id] = [R].[id]
						   AND [R_N].[codice_notifica] = 'SLA_PROSP'
					)
	   
END
GO
/****** Object:  StoredProcedure [dbo].[SP_GetUffGaranzieDashNumeriByRole]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 01/04/2022
-- Description:	Recupera i valori riassuntivi della dashboard in base al ruolo
-- =============================================
CREATE PROCEDURE [dbo].[SP_GetUffGaranzieDashNumeriByRole] 
	@idRole INT,
	@isAdmin BIT,
	@selectedBusiness NVARCHAR(100) = ''''''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	CREATE TABLE #AvaiableBusiness ([business] NVARCHAR(10) NOT NULL);
	INSERT #AvaiableBusiness ([business])
	SELECT DISTINCT [W].[business]
			   FROM [dbo].[workflow_step_roles] [W_S_R]
			   JOIN [dbo].[Workflow_step] [W_S] ON [W_S_R].[workflow_step_id] = [W_S].[id]
			   JOIN [dbo].[Workflow] [W] ON [W].[id] = [W_S].[workflow_id]
			  WHERE [role_id] = @idRole


	DECLARE @sql NVARCHAR(MAX) = '
    SELECT 1 as box, count(*) as primario, 
		   convert(varchar(19),
		   ISNULL(max(r.[last_mod_date]), max(r.[create_date])), 120) as secondario 
	  FROM [dbo].[cliente] [C]
	  JOIN [dbo].[richiesta] [R] ON [C].[id] = [R].[cliente_id]
	  JOIN [Anagraphics].[Cliente_Business] [C_B] ON [C_B].[cliente_id] = [C].[id]
	  JOIN [Anagraphics].[Business] [B] ON [B].[id] = [C_B].[business_id]
	 WHERE (
				[R].[stato_id] = -1 
			 OR [R].[stato_id] IS NULL
			 OR [R].[stato_id] = (
				SELECT [id] FROM [Procedural].[FC_GetDefaultWorkflowStep]([B].[name])
			  )
			)
		AND (
			   @isAdmin = 1 OR
			   [B].[name] IN (
					SELECT [business] FROM #AvaiableBusiness
				)
			)
		AND [B].[name] IN (' + @selectedBusiness + ')
	UNION
	SELECT 2 as box,
		  (SELECT COUNT(*)
			 FROM [dbo].[richiesta] [R]
			 JOIN [Anagraphics].[Cliente_Business] [C_B] ON [C_B].[cliente_id] = [R].[cliente_id]
			 JOIN [Anagraphics].[Business] [B] ON [B].[id] = [C_B].[business_id]
			WHERE [R].[stato_id] IN (1,4)
			 AND (
					@isAdmin = 1 OR
					[B].[name] IN (
						SELECT [business] FROM #AvaiableBusiness
					)
				)
			 AND [B].[name] IN (' + @selectedBusiness + ')
			),
		  convert(varchar(50),
			(
				SELECT COUNT(*)
				  FROM [dbo].[richiesta] [R]
				  JOIN [Anagraphics].[Cliente_Business] [C_B] ON [C_B].[cliente_id] = [R].[cliente_id]
				  JOIN [Anagraphics].[Business] [B] ON [B].[id] = [C_B].[business_id]
				 WHERE [R].[stato_id] IS NOT NULL
				   AND (
						   @isAdmin = 1 OR
						   [B].[name] IN (
								SELECT [business] FROM #AvaiableBusiness
							)
						)
				   AND [B].[name] IN (' + @selectedBusiness + ')
			)
		)
	UNION
	SELECT 3 as box,
		   COUNT([G].[id]),
		   convert(varchar(50),ISNULL(SUM([G].[dovuto]),0))
	  FROM [PortaleCredito].[dbo].[garanzie] [G]
	  JOIN [Statistics].[StatoScadenzaGaranzia] [SG] ON [SG].[garanzia_id] = [G].[id]
	  JOIN [dbo].[richiesta] [R] ON [G].[richiesta_id] = [R].[id]
	  JOIN [Anagraphics].[Cliente_Business] [C_B] ON [C_B].[cliente_id] = [R].[cliente_id]
	  JOIN [Anagraphics].[Business] [B] ON [B].[id] = [C_B].[business_id]
	 WHERE [SG].[in_scadenza] = 1
	   AND [SG].[scaduta] = 0
	   AND (
				@isAdmin = 1 OR
				[B].[name] IN (
					SELECT [business] FROM #AvaiableBusiness
				)
			)
		AND [B].[name] IN (' + @selectedBusiness + ')
	UNION
	SELECT 4 AS box,
		   COUNT([G].[id]),
		   convert(varchar(19),MAX([G].[last_mod_date]),120) as secondario
	  FROM [PortaleCredito].[dbo].[garanzie] [G]
	  JOIN [dbo].[richiesta] [R] ON [G].[richiesta_id] = [R].[id]
	  JOIN [Anagraphics].[Cliente_Business] [C_B] ON [C_B].[cliente_id] = [R].[cliente_id]
	  JOIN [Anagraphics].[Business] [B] ON [B].[id] = [C_B].[business_id]
	 WHERE [G].[create_user] = ''ImportNewGaranzieAPI''
	   AND (
					@isAdmin = 1 OR
					[B].[name] IN (
						SELECT [business] FROM #AvaiableBusiness
					)
				)
	   AND [B].[name] IN (' + @selectedBusiness + ') 
	   AND [R].[stato_id] != (
							 SELECT TOP(1) [id]
									  FROM [dbo].[stato_richiesta]
									 WHERE [nome] = ''Attivo''
						 )
	UNION
	SELECT 5 as box,
		   COUNT([G].[id]),
		   convert(varchar(50),ISNULL(SUM([G].[dovuto]),0))
	  FROM [PortaleCredito].[dbo].[garanzie] [G]
	  JOIN [Statistics].[StatoScadenzaGaranzia] [SG] ON [SG].[garanzia_id] = [G].[id]
	  JOIN [dbo].[richiesta] [R] ON [G].[richiesta_id] = [R].[id]
	  JOIN [Anagraphics].[Cliente_Business] [C_B] ON [C_B].[cliente_id] = [R].[cliente_id]
	  JOIN [Anagraphics].[Business] [B] ON [B].[id] = [C_B].[business_id]
	 WHERE [SG].[scaduta] = 1
	   AND (
					@isAdmin = 1 OR
					[B].[name] IN (
						SELECT [business] FROM #AvaiableBusiness
					)
				)
	   AND [B].[name] IN (' + @selectedBusiness + ')';

		  --SELECT @sql

	DECLARE @params NVARCHAR(4000) = '@isAdmin BIT'

	EXEC sp_executesql @sql, @params, @isAdmin = @isAdmin
END
GO
/****** Object:  StoredProcedure [Logs].[SP_CreateNotificheAppByCodice]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 17/12/2021
-- Description:	Inserisce le notifiche app agli utenti a cui è assegnata
-- =============================================
CREATE PROCEDURE [Logs].[SP_CreateNotificheAppByCodice]
	@CodiceNotifica NVARCHAR(10),
	@Message NVARCHAR(500),
	@UrlToOpen NVARCHAR(500) = '',
	@UtenteOperazione nvarchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
					
	DECLARE @DataAttuale DATETIME = GETDATE();

	INSERT INTO [Logs].[HistoryNotifiche]
			   ([utente_id]
			   ,[gruppo_id]
			   ,[notifica_id]
			   ,[is_new]
			   ,[message]
			   ,[url_to_open]
			   ,[create_user]
			   ,[create_date])
		SELECT
			[IdUtentiList].[utente_id],
			NULL,
			[IdUtentiList].[notifica_id],
			1,
			@Message,
			@UrlToOpen,
			@UtenteOperazione,
			@DataAttuale
			FROM
				(
				SELECT
					[UT].[id] AS [utente_id],
					[NT].[id] AS [notifica_id]
				FROM
					[dbo].[notifica] [NT]
				JOIN
					[dbo].[tipologia_notifica] [TN]
				ON
					[TN].[id] = [NT].[tipologia_id]
				JOIN
					[dbo].[notifica_utente] [NU]
				ON
					[NU].[notifica_id] = [NT].[id]
				JOIN
					[Anagraphics].[Users] [UT]
				ON
					[UT].[id] = [NU].[utente_id]
				WHERE
					[NT].[codice] = @CodiceNotifica
				AND
					[TN].[nome] = 'APP'
			UNION
				SELECT
					[UT].[id] AS [utente_id],
					[NT].[id] AS [notifica_id]
				FROM
					[dbo].[notifica] [NT]
				JOIN
					[dbo].[tipologia_notifica] [TN]
				ON
					[TN].[id] = [NT].[tipologia_id]
				JOIN
					[dbo].[notifica_gruppo] [NG]
				ON
					[NG].[notifica_id] = [NT].[id]
				JOIN
					[dbo].[utenti_gruppi] [UG]
				ON 
					[UG].[gruppo_id] = [NG].[gruppo_id]
				JOIN
					[Anagraphics].[Users] [UT]
				ON
					[UT].[id] = [UG].[utente_id]
				WHERE
					[NT].[codice] = @CodiceNotifica
				AND
					[TN].[nome] = 'APP'
				GROUP BY
					[UT].[id],
					[NT].[id]
			) AS [IdUtentiList]
END
GO
/****** Object:  StoredProcedure [Logs].[SP_CreateNotificheAppByNotificaId]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 17/12/2021
-- Description:	Inserisce le notifiche app agli utenti a cui è assegnata
-- =============================================
CREATE PROCEDURE [Logs].[SP_CreateNotificheAppByNotificaId]
	@NotificaId INT,
	@Message NVARCHAR(500),
	@UrlToOpen NVARCHAR(500) = '',
	@UtenteOperazione nvarchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
					
	DECLARE @DataAttuale DATETIME = GETDATE();

	INSERT INTO [Logs].[HistoryNotifiche]
			   ([utente_id]
			   ,[gruppo_id]
			   ,[notifica_id]
			   ,[is_new]
			   ,[message]
			   ,[url_to_open]
			   ,[create_user]
			   ,[create_date])
		SELECT
			[IdUtentiList].[utente_id],
			NULL,
			[IdUtentiList].[notifica_id],
			1,
			@Message,
			@UrlToOpen,
			@UtenteOperazione,
			@DataAttuale
			FROM
				(
				SELECT
					[UT].[id] AS [utente_id],
					[NT].[id] AS [notifica_id]
				FROM
					[dbo].[notifica] [NT]
				JOIN
					[dbo].[tipologia_notifica] [TN]
				ON
					[TN].[id] = [NT].[tipologia_id]
				JOIN
					[dbo].[notifica_utente] [NU]
				ON
					[NU].[notifica_id] = [NT].[id]
				JOIN
					[Anagraphics].[Users] [UT]
				ON
					[UT].[id] = [NU].[utente_id]
				WHERE
					[NT].[id] = @NotificaId
				AND
					[TN].[nome] = 'APP'
			UNION
				SELECT
					[UT].[id] AS [utente_id],
					[NT].[id] AS [notifica_id]
				FROM
					[dbo].[notifica] [NT]
				JOIN
					[dbo].[tipologia_notifica] [TN]
				ON
					[TN].[id] = [NT].[tipologia_id]
				JOIN
					[dbo].[notifica_gruppo] [NG]
				ON
					[NG].[notifica_id] = [NT].[id]
				JOIN
					[dbo].[utenti_gruppi] [UG]
				ON 
					[UG].[gruppo_id] = [NG].[gruppo_id]
				JOIN
					[Anagraphics].[Users] [UT]
				ON
					[UT].[id] = [UG].[utente_id]
				WHERE
					[NT].[id] = @NotificaId
				AND
					[TN].[nome] = 'APP'
				GROUP BY
					[UT].[id],
					[NT].[id]
			) AS [IdUtentiList]
END
GO
/****** Object:  StoredProcedure [Logs].[SP_CreateNotificheAppByStepId]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 17/12/2021
-- Description:	Inserisce le notifiche app agli utenti a cui è assegnata
-- =============================================
CREATE PROCEDURE [Logs].[SP_CreateNotificheAppByStepId]
	@StepId INT,
	@Message NVARCHAR(500),
	@UrlToOpen NVARCHAR(500) = '',
	@UtenteOperazione nvarchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
					
	DECLARE @DataAttuale DATETIME = GETDATE();

	INSERT INTO [Logs].[HistoryNotifiche]
			   ([utente_id]
			   ,[gruppo_id]
			   ,[notifica_id]
			   ,[is_new]
			   ,[message]
			   ,[url_to_open]
			   ,[create_user]
			   ,[create_date])
		SELECT
			[IdUtentiList].[utente_id],
			NULL,
			[IdUtentiList].[notifica_id],
			1,
			@Message,
			@UrlToOpen,
			@UtenteOperazione,
			@DataAttuale
			FROM
				(
				SELECT
					[UT].[id] AS [utente_id],
					[NT].[id] AS [notifica_id]
				FROM
					[dbo].[notifica] [NT]
				JOIN
					[dbo].[tipologia_notifica] [TN]
				ON
					[TN].[id] = [NT].[tipologia_id]
				JOIN
					[dbo].[notifica_utente] [NU]
				ON
					[NU].[notifica_id] = [NT].[id]
				JOIN
					[Anagraphics].[Users] [UT]
				ON
					[UT].[id] = [NU].[utente_id]
				WHERE
					[NT].[step_id] = @StepId
				AND
					[TN].[nome] = 'APP'
			UNION
				SELECT
					[UT].[id] AS [utente_id],
					[NT].[id] AS [notifica_id]
				FROM
					[dbo].[notifica] [NT]
				JOIN
					[dbo].[tipologia_notifica] [TN]
				ON
					[TN].[id] = [NT].[tipologia_id]
				JOIN
					[dbo].[notifica_gruppo] [NG]
				ON
					[NG].[notifica_id] = [NT].[id]
				JOIN
					[dbo].[utenti_gruppi] [UG]
				ON 
					[UG].[gruppo_id] = [NG].[gruppo_id]
				JOIN
					[Anagraphics].[Users] [UT]
				ON
					[UT].[id] = [UG].[utente_id]
				WHERE
					[NT].[step_id] = @StepId
				AND
					[TN].[nome] = 'APP'
				GROUP BY
					[UT].[id],
					[NT].[id]
			) AS [IdUtentiList]
END
GO
/****** Object:  StoredProcedure [Logs].[SP_DeleteNotificaGruppiByIdNotifica]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 09/02/2022
-- Description:	Rimuove tuttle assegnazioni Gruppi-Notifica in base alla notifica
-- =============================================
CREATE PROCEDURE [Logs].[SP_DeleteNotificaGruppiByIdNotifica]
	@IdNotifica INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DELETE FROM [dbo].[notifica_gruppo]
		  WHERE [notifica_id] = @IdNotifica
END
GO
/****** Object:  StoredProcedure [Logs].[SP_DeleteNotificaUtenteByIdNotifica]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 09/02/2022
-- Description:	Rimuove tuttle assegnazioni Utenti-Notifica in base alla notifica
-- =============================================
CREATE PROCEDURE [Logs].[SP_DeleteNotificaUtenteByIdNotifica]
	@IdNotifica INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DELETE FROM [dbo].[notifica_utente]
		  WHERE [notifica_id] = @IdNotifica
END
GO
/****** Object:  StoredProcedure [Logs].[SP_GetDestinatariEmailByCodiceNotifica]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 17/12/2021
-- Description:	Recupera la lista dei destinatari di una notifica
-- =============================================
CREATE PROCEDURE [Logs].[SP_GetDestinatariEmailByCodiceNotifica]
	@CodiceNotifica NVARCHAR(10)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

			SELECT
				[UT].[email] AS [email]
			FROM
				[dbo].[notifica] [NT] (NOLOCK) 
			JOIN
				[dbo].[tipologia_notifica] [TN] (NOLOCK) 
			ON
				[TN].[id] = [NT].[tipologia_id]
			JOIN
				[dbo].[notifica_utente] [NU] (NOLOCK) 
			ON
				[NU].[notifica_id] = [NT].[id]
			JOIN
				[Anagraphics].[Users] [UT] (NOLOCK) 
			ON
				[UT].[id] = [NU].[utente_id]
			WHERE
				[NT].[codice] = @CodiceNotifica
			AND
				[TN].[nome] = 'EMAIL'
		UNION
			SELECT
				[UT].[email] AS [email]
			FROM
				[dbo].[notifica] [NT] (NOLOCK) 
			JOIN
				[dbo].[tipologia_notifica] [TN] (NOLOCK) 
			ON
				[TN].[id] = [NT].[tipologia_id]
			JOIN
				[dbo].[notifica_gruppo] [NG] (NOLOCK) 
			ON
				[NG].[notifica_id] = [NT].[id]
			JOIN
				[dbo].[utenti_gruppi] [UG] (NOLOCK) 
			ON 
				[UG].[gruppo_id] = [NG].[gruppo_id]
			JOIN
				[Anagraphics].[Users] [UT] (NOLOCK) 
			ON
				[UT].[id] = [UG].[utente_id]
			WHERE
				[NT].[codice] = @CodiceNotifica
			AND
				[TN].[nome] = 'EMAIL'
			GROUP BY
				[email]
END
GO
/****** Object:  StoredProcedure [Logs].[SP_GetDestinatariNotificaRichiestaSLA]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 31/03/2022
-- Description:	Recupera la lista dei destinatari della notifica di superamento SLA
-- =============================================
CREATE PROCEDURE [Logs].[SP_GetDestinatariNotificaRichiestaSLA]
	@RichiestaId INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

		SELECT 
				[U].*
		  FROM
				[dbo].[richiesta] [R] (NOLOCK) 
		  JOIN
				[dbo].[workflow_step_roles] [W_S_R] (NOLOCK) 
		  ON
				[W_S_R].[workflow_step_id] = [R].[workflow_step_id]
		  JOIN
				[Anagraphics].[Users] [U] (NOLOCK)
		  ON
				[U].[role_id] = [W_S_R].[role_id]
		  WHERE
				[R].[id] = @RichiestaId
END
GO
/****** Object:  StoredProcedure [Logs].[SP_GetDestinatariNotificheByCodice]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 28/02/2022
-- Description:	Recupera la lista dei destinatari di una richiesta
-- =============================================
CREATE PROCEDURE [Logs].[SP_GetDestinatariNotificheByCodice]
	@CodiceNotifica NVARCHAR(10),
	@TipologiaNotifica NVARCHAR(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	SELECT [U].*
	  FROM [Anagraphics].[Users] [U]
	 WHERE [U].[id] IN (
						SELECT
							[NU].[utente_id] AS [utente_id]
						FROM
							[dbo].[notifica] [NT] (NOLOCK) 
						JOIN
							[dbo].[tipologia_notifica] [TN] (NOLOCK) 
						ON
							[TN].[id] = [NT].[tipologia_id]
						JOIN
							[dbo].[notifica_utente] [NU] (NOLOCK) 
						ON
							[NU].[notifica_id] = [NT].[id]
						WHERE
							[NT].[codice] = @CodiceNotifica
						AND
							[TN].[nome] = @TipologiaNotifica
					UNION
						SELECT
							[UG].[utente_id] AS [utente_id]
						FROM
							[dbo].[notifica] [NT] (NOLOCK) 
						JOIN
							[dbo].[tipologia_notifica] [TN] (NOLOCK) 
						ON
							[TN].[id] = [NT].[tipologia_id]
						JOIN
							[dbo].[notifica_gruppo] [NG] (NOLOCK) 
						ON
							[NG].[notifica_id] = [NT].[id]
						JOIN
							[dbo].[utenti_gruppi] [UG] (NOLOCK) 
						ON 
							[UG].[gruppo_id] = [NG].[gruppo_id]
						WHERE
							[NT].[codice] = @CodiceNotifica
						AND
							[TN].[nome] = @TipologiaNotifica
						GROUP BY
							 [utente_id]
					)
END
GO
/****** Object:  StoredProcedure [Logs].[SP_GetDestinatariNotificheByRichiestaId]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 09/12/2021
-- Description:	Carica un utente
-- =============================================
CREATE PROCEDURE [Logs].[SP_GetDestinatariNotificheByRichiestaId]
	@richiesta_id int
AS
BEGIN


--SET @richiesta_id as int = 4

select ntf_u.utente_id as utente_id, ute.email, ntf.tipologia_id, tip.nome
from [dbo].[richiesta] (nolock)  ric
inner join [dbo].[notifica]  (nolock)  ntf
on ntf.step_id = ric.workflow_step_id
inner join [dbo].[notifica_utente]  (nolock)  ntf_u
on ntf_u.notifica_id = ntf.id 
inner join [dbo].[tipologia_notifica] tip
on tip.id = ntf.tipologia_id
inner join [Anagraphics].[Users] ute
on ute.id = ntf_u.utente_id
where ric.id = @richiesta_id

union

select ute_g.utente_id as utente_id, ute.email, ntf.tipologia_id, tip.nome
from [dbo].[richiesta] (nolock)  ric
inner join [dbo].[notifica]  (nolock)  ntf
on ntf.step_id = ric.workflow_step_id
inner join [dbo].[notifica_gruppo]  (nolock)  ntf_g
on ntf_g.notifica_id = ntf.id
inner join [Anagraphics].[Gruppi]  (nolock)  grp
on grp.id = ntf_g.gruppo_id
inner join [dbo].[utenti_gruppi]  (nolock)  ute_g
on grp.id = ute_g.gruppo_id
inner join [dbo].[tipologia_notifica] tip
on tip.id = ntf.tipologia_id
inner join [Anagraphics].[Users] ute
on ute.id = ute_g.utente_id
where ric.id = @richiesta_id


END
GO
/****** Object:  StoredProcedure [Logs].[SP_GetDestinatariNotificheByStepId]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 28/02/2022
-- Description:	Recupera la lista dei destinatari di una richiesta
-- =============================================
CREATE PROCEDURE [Logs].[SP_GetDestinatariNotificheByStepId]
	@IdStep INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

			SELECT
				[UT].[email] AS [email]
			FROM
				[dbo].[notifica] [NT] (NOLOCK) 
			JOIN
				[dbo].[tipologia_notifica] [TN] (NOLOCK) 
			ON
				[TN].[id] = [NT].[tipologia_id]
			JOIN
				[dbo].[notifica_utente] [NU] (NOLOCK) 
			ON
				[NU].[notifica_id] = [NT].[id]
			JOIN
				[Anagraphics].[Users] [UT] (NOLOCK) 
			ON
				[UT].[id] = [NU].[utente_id]
			WHERE
				[NT].[step_id] = @IdStep
			AND
				[TN].[nome] = 'EMAIL'
		UNION
			SELECT
				[UT].[email] AS [email]
			FROM
				[dbo].[notifica] [NT] (NOLOCK) 
			JOIN
				[dbo].[tipologia_notifica] [TN] (NOLOCK) 
			ON
				[TN].[id] = [NT].[tipologia_id]
			JOIN
				[dbo].[notifica_gruppo] [NG] (NOLOCK) 
			ON
				[NG].[notifica_id] = [NT].[id]
			JOIN
				[dbo].[utenti_gruppi] [UG] (NOLOCK) 
			ON 
				[UG].[gruppo_id] = [NG].[gruppo_id]
			JOIN
				[Anagraphics].[Users] [UT] (NOLOCK) 
			ON
				[UT].[id] = [UG].[utente_id]
			WHERE
				[NT].[step_id] = @IdStep
			AND
				[TN].[nome] = 'EMAIL'
			GROUP BY
				[email]
END
GO
/****** Object:  StoredProcedure [Logs].[SP_GetHistoryNotificaById]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 13/12/2021
-- Description:	Carica una notificaUtente notifiche di utente
-- =============================================
CREATE PROCEDURE [Logs].[SP_GetHistoryNotificaById]
	@IdNotifica INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	SELECT [HN].[id]
		  ,[HN].[message]
		  ,[HN].[url_to_open]
		  ,[HN].[is_new]
		  ,[HN].[notifica_id]
		  ,[NT].[step_id] [notifica_step_id]
		  ,[NT].[nome] [notifica_nome]
		  ,[NT].[tipologia_id]
		  ,[TN].[nome] [tipologia_nome]
		  ,[TN].[descrizione] [tipologia_descrizione]
	  FROM
			[Logs].[HistoryNotifiche] [HN]
	  JOIN
			[dbo].[notifica] [NT]
		ON
			[NT].[id] = [HN].[notifica_id]
	  JOIN
			[dbo].[tipologia_notifica] [TN]
		ON
			[TN].[id] = [NT].[tipologia_id]
	  WHERE (
			[HN].[id] = @IdNotifica
		)


END
GO
/****** Object:  StoredProcedure [Logs].[SP_GetHistoryNotificheBySteId_BAD]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 13/12/2021
-- Description:	Carica le notifiche relativo ad uno specifico step approvativo
-- =============================================
CREATE PROCEDURE [Logs].[SP_GetHistoryNotificheBySteId_BAD]
	@IdStep INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	SELECT [HN].[id]
		  ,[HN].[message]
		  ,[HN].[url_to_open]
		  ,[HN].[is_new]
		  ,[HN].[notifica_id]
		  ,[NT].[step_id] [notifica_step_id]
		  ,[NT].[nome] [notifica_nome]
		  ,[NT].[tipologia_id]
		  ,[TN].[nome] [tipologia_nome]
		  ,[TN].[descrizione] [tipologia_descrizione]
	  FROM
			[Logs].[HistoryNotifiche] [HN]
	  JOIN
			[dbo].[notifica] [NT]
		ON
			[NT].[id] = [HN].[notifica_id]
	  JOIN
			[dbo].[tipologia_notifica] [TN]
		ON
			[TN].[id] = [NT].[tipologia_id]
	  WHERE 
			[NT].[step_id] = @IdStep

END
GO
/****** Object:  StoredProcedure [Logs].[SP_GetHistoryNotificheUtente]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 13/12/2021
-- Description:	Carica le notifiche di utente
-- =============================================
CREATE PROCEDURE [Logs].[SP_GetHistoryNotificheUtente]
	@IdUtente INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @IdGruppo INT = ISNULL((
		SELECT TOP(1) [gruppo_id]
		  FROM [dbo].[utenti_gruppi]
		 WHERE [utente_id] = @IdUtente
	),-1);

	SELECT [HN].[id]
		  ,[HN].[utente_id]
		  ,[HN].[message]
		  ,[HN].[url_to_open]
		  ,[HN].[is_new]
		  ,[HN].[notifica_id]
		  ,[NT].[step_id] [notifica_step_id]
		  ,[NT].[nome] [notifica_nome]
		  ,[NT].[tipologia_id]
		  ,[TN].[nome] [tipologia_nome]
		  ,[TN].[descrizione] [tipologia_descrizione]
	  FROM
			[Logs].[HistoryNotifiche] [HN]
	  JOIN
			[dbo].[notifica] [NT]
		ON
			[NT].[id] = [HN].[notifica_id]
	  JOIN
			[dbo].[tipologia_notifica] [TN]
		ON
			[TN].[id] = [NT].[tipologia_id]
	  WHERE (
			[utente_id] = @IdUtente OR
			[gruppo_id] = @IdGruppo
		)


END
GO
/****** Object:  StoredProcedure [Logs].[SP_SetReadedHistoryNotificaById]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 13/12/2021
-- Description:	Imposta come lette una notifica
-- =============================================
CREATE PROCEDURE [Logs].[SP_SetReadedHistoryNotificaById]
	@IdNotifica INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	UPDATE [Logs].[HistoryNotifiche]
	   SET [is_new] = 0
	 WHERE [id] = @IdNotifica

END
GO
/****** Object:  StoredProcedure [Logs].[SP_SetReadedHistoryNotificheByUtente]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 13/05/2022
-- Description:	Imposta come lette tutte le notifiche di un utente
-- =============================================
CREATE PROCEDURE [Logs].[SP_SetReadedHistoryNotificheByUtente]
	@IdUtente INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	UPDATE [Logs].[HistoryNotifiche]
	   SET [is_new] = 0
	 WHERE [utente_id] = @IdUtente

END
GO
/****** Object:  StoredProcedure [Procedural].[SP_UpdateRichiestaDerogaContratto]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Diego Capone
-- Create date: 11/05/2022
-- Description:	Salva i valori relativi alla deroga contratto dello Step3 del processo di Valutazione
-- =============================================
CREATE PROCEDURE [Procedural].[SP_UpdateRichiestaDerogaContratto]
	@idRichiesta INT,
	@workflowStepId INT = -1,
	@businessName NVARCHAR(10),
	@derogaContrattoId INT = NULL,
	@ipAddress NVARCHAR(15) = '',
	@workUserId INT = -1,
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @ActualStepIndex INT = ISNULL((
									SELECT [WS].[tab_index]
									  FROM [dbo].[Workflow_step] [WS]
									 WHERE [WS].[id] = @workflowStepId
								),-1);

	INSERT INTO [dbo].[richiesta_hst]
			   ([richiesta_id]
			   ,[cliente_id]
			   ,[codice_sala]
			   ,[codice_tir]
			   ,[workflow_step_id]
			   ,[stato_id]
			   ,[stato_sap_id]
			   ,[data_attivazione]
			   ,[categoria_id]
			   ,[presenza_societa_collegate]
			   ,[societa_collegate]
			   ,[presenza_ultimo_bilancio]
			   ,[anno_bilancio]
			   ,[presenza_eventi_negativi]
			   ,[eventi_negativi]
			   ,[presenza_esiti_pregressi]
			   ,[side_letter]
			   ,[canale_contratto_id]
			   ,[canale_sottoscrizione_id]
			   ,[deroga_contratto_id]
			   ,[deroga_merito_user]
			   ,[deroga_merito_date]
			   ,[deroga_merito_id]
			   ,[deroga_merito_nota_id]
			   ,[salvata]
			   ,[work_user_id]
			   ,[work_user]
			   ,[work_date]
			   ,[create_user]
			   ,[create_date]
			   ,[last_mod_user]
			   ,[last_mod_date]
			   ,[ip_address])
		SELECT [id]
			  ,[cliente_id]
			  ,[codice_sala]
			  ,[codice_tir]
			  ,[workflow_step_id]
			  ,[stato_id]
			  ,[stato_sap_id]
			  ,[data_attivazione]
			  ,[categoria_id]
			  ,[presenza_societa_collegate]
			  ,[societa_collegate]
			  ,[presenza_ultimo_bilancio]
			  ,[anno_bilancio]
			  ,[presenza_eventi_negativi]
			  ,[eventi_negativi]
			  ,[presenza_esiti_pregressi]
			  ,[side_letter]
			  ,[canale_contratto_id]
			  ,[canale_sottoscrizione_id]
			  ,[deroga_contratto_id]
			  ,[deroga_merito_user]
			  ,[deroga_merito_date]
			  ,[deroga_merito_id]
			  ,[deroga_merito_nota_id]
			  ,[salvata]
			  ,[work_user_id]
			  ,[work_user]
			  ,[work_date]
			  ,[create_user]
			  ,[create_date]
			  ,[last_mod_user]
			  ,[last_mod_date]
			  ,@ipAddress
		  FROM [dbo].[richiesta]
		 WHERE [id] = @idRichiesta

	DECLARE @StatoId INT = ISNULL((
				SELECT TOP(1) [id]
				  FROM [dbo].[stato_richiesta]
				 WHERE [nome] = 'Attivo con Deroga'
		),-1);

	UPDATE [dbo].[richiesta]
	   SET [deroga_contratto_id] = @derogaContrattoId
		  ,[stato_id] = CASE WHEN @StatoId > 0 THEN @StatoId ELSE [stato_id] END
		  ,[last_mod_user] = @utenteUpdate
		  ,[last_mod_date] = @DataAttuale
		  ,[work_user_id] = @workUserId
		  ,[work_user] = @utenteUpdate
		  ,[work_date] = @DataAttuale
	 WHERE [id] = @idRichiesta

	 SELECT [return_id] = @ActualStepIndex
END
GO
/****** Object:  StoredProcedure [Procedural].[SP_UpdateRichiestaDerogaMerito]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 27/01/2022
-- Description:	Salva i valori relativi allo Step2 del processo di Valutazione nel caso in cui si inserisce una deroga
-- =============================================
CREATE PROCEDURE [Procedural].[SP_UpdateRichiestaDerogaMerito]
	@idRichiesta INT,
	@workflowStepId INT = -1,
	@statoId INT,
	@businessName NVARCHAR(10),
	@notaId INT = -1,
	@noteDeroga NVARCHAR(MAX) = '',
	@ipAddress NVARCHAR(15) = '',
	@workUserId INT = -1,
	@derogaMeritoId INT = -1,
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @NextStepId INT = -1,
			@NextStepIndex INT = -1,
			@ActualStepIndex INT = ISNULL((
									SELECT [WS].[tab_index]
									  FROM [dbo].[Workflow_step] [WS]
									 WHERE [WS].[id] = @workflowStepId
								),-1);

	DECLARE @NomeStatoRichiesta NVARCHAR(50) = ISNULL((
															SELECT TOP(1) [nome]
															  FROM [dbo].[stato_richiesta]
															  WHERE [id] = @statoId
														),'');

	
	SELECT @NextStepId = ISNULL([WN].[workflow_prossimo_step_id],-1),
		   @NextStepIndex = ISNULL([WS].[tab_index],-1)
	  FROM [dbo].[Workflow_navigazione] [WN]
	  JOIN [dbo].[Workflow_step] [WS] ON [WS].[id] = [WN].[workflow_prossimo_step_id]
	 WHERE [workflow_step_id] = @workflowStepId

	IF(@NextStepId = -1)
	BEGIN
		SELECT TOP(1) 
			   @NextStepId = ISNULL([Id],-1),
			   @NextStepIndex = ISNULL([tab_index],-1)
		  FROM [Procedural].[FC_GetDefaultWorkflowStep](@businessName)
	END

	IF(@notaId = -1 AND LTRIM(RTRIM(@noteDeroga)) != '')
		BEGIN
			-- CREO LA NOTA

			INSERT INTO [dbo].[note_richiesta]
					   ([richiesta_id]
					   ,[workflow_step_id]
					   ,[note]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@idRichiesta
					   ,@workflowStepId
					   ,LTRIM(RTRIM(@noteDeroga))
					   ,@utenteUpdate
					   ,@DataAttuale)

			SET @notaId = SCOPE_IDENTITY();
		END
	ELSE IF(@notaId > 0)
		BEGIN
			-- AGGIORNO LA NOTA

			UPDATE [dbo].[note_richiesta]
			   SET [note] = LTRIM(RTRIM(@noteDeroga))
				  ,[last_mod_user] = @utenteUpdate
				  ,[last_mod_date] = @DataAttuale
			 WHERE [id] = @notaId
		END

	INSERT INTO [dbo].[richiesta_hst]
			   ([richiesta_id]
			   ,[cliente_id]
			   ,[codice_sala]
			   ,[codice_tir]
			   ,[workflow_step_id]
			   ,[stato_id]
			   ,[stato_sap_id]
			   ,[data_attivazione]
			   ,[categoria_id]
			   ,[presenza_societa_collegate]
			   ,[societa_collegate]
			   ,[presenza_ultimo_bilancio]
			   ,[anno_bilancio]
			   ,[presenza_eventi_negativi]
			   ,[eventi_negativi]
			   ,[presenza_esiti_pregressi]
			   ,[side_letter]
			   ,[canale_contratto_id]
			   ,[canale_sottoscrizione_id]
			   ,[deroga_contratto_id]
			   ,[deroga_merito_user]
			   ,[deroga_merito_date]
			   ,[deroga_merito_id]
			   ,[deroga_merito_nota_id]
			   ,[salvata]
			   ,[work_user_id]
			   ,[work_user]
			   ,[work_date]
			   ,[create_user]
			   ,[create_date]
			   ,[last_mod_user]
			   ,[last_mod_date]
			   ,[ip_address])
		SELECT [id]
			  ,[cliente_id]
			  ,[codice_sala]
			  ,[codice_tir]
			  ,[workflow_step_id]
			  ,[stato_id]
			  ,[stato_sap_id]
			  ,[data_attivazione]
			  ,[categoria_id]
			  ,[presenza_societa_collegate]
			  ,[societa_collegate]
			  ,[presenza_ultimo_bilancio]
			  ,[anno_bilancio]
			  ,[presenza_eventi_negativi]
			  ,[eventi_negativi]
			  ,[presenza_esiti_pregressi]
			  ,[side_letter]
			  ,[canale_contratto_id]
			  ,[canale_sottoscrizione_id]
			  ,[deroga_contratto_id]
			  ,[deroga_merito_user]
			  ,[deroga_merito_date]
			  ,[deroga_merito_id]
			  ,[deroga_merito_nota_id]
			  ,[salvata]
			  ,[work_user_id]
			  ,[work_user]
			  ,[work_date]
			  ,[create_user]
			  ,[create_date]
			  ,[last_mod_user]
			  ,[last_mod_date]
			  ,@ipAddress
		  FROM [dbo].[richiesta]
		 WHERE [id] = @idRichiesta

	UPDATE [dbo].[richiesta]
	   SET [workflow_step_id] = CASE WHEN @NextStepIndex >= @ActualStepIndex THEN @NextStepId ELSE [workflow_step_id] END
		  ,[stato_id] = CASE WHEN @NextStepIndex >= @ActualStepIndex THEN @statoId ELSE [stato_id] END
		  ,[last_mod_user] = CASE WHEN @NextStepIndex >= @ActualStepIndex THEN @utenteUpdate ELSE [last_mod_user] END
		  ,[last_mod_date] = CASE WHEN @NextStepIndex >= @ActualStepIndex THEN @DataAttuale ELSE [last_mod_date] END
		  ,[work_user_id] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @workUserId ELSE [work_user_id] END
		  ,[work_user] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @utenteUpdate ELSE [work_user] END
		  ,[work_date] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @DataAttuale ELSE [work_date] END
		  ,[deroga_merito_user] = CASE WHEN @NextStepIndex >= @ActualStepIndex THEN @utenteUpdate ELSE [deroga_merito_user] END
		  ,[deroga_merito_date] = CASE WHEN @NextStepIndex >= @ActualStepIndex THEN @DataAttuale ELSE [deroga_merito_date] END
		  ,[deroga_merito_id] = CASE WHEN @NextStepIndex > @ActualStepIndex AND @derogaMeritoId > 0 THEN @derogaMeritoId ELSE [deroga_merito_id] END
		  ,[deroga_merito_nota_id] = CASE WHEN @NextStepIndex > @ActualStepIndex AND @notaId > 0 THEN @notaId ELSE [deroga_merito_nota_id] END
	 WHERE [id] = @idRichiesta

	  SELECT TOP(1) [return_id] = @NextStepId
END
GO
/****** Object:  StoredProcedure [Procedural].[SP_UpdateRichiestaStep1]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 27/01/2022
-- Description:	Salva i valori relativi allo Step1 del processo di Valutazione
-- =============================================
CREATE PROCEDURE [Procedural].[SP_UpdateRichiestaStep1]
	@idRichiesta INT,
	@categoriaId INT,
	@workflowStepId INT = -1,
	@businessName NVARCHAR(10),
	@presenzaSocieta BIT,
	@societaCollegate NVARCHAR(500),
	@presenzaBilancio BIT,
	@annoBilancio INT,
	@presenzaEventiNegativi BIT,
	@eventiNegativi NVARCHAR(500),
	@presenzaEsitiPregressi BIT,
	@ipAddress NVARCHAR(15) = '',
	@workUserId INT = -1,
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @NextStepId INT = -1,
			@NextStepIndex INT = -1,
			@ActualStepIndex INT = ISNULL((
									SELECT [WS].[tab_index]
									  FROM [dbo].[Workflow_step] [WS]
									 WHERE [WS].[id] = @workflowStepId
								),-1);


	SELECT @NextStepId = ISNULL([WN].[workflow_prossimo_step_id],-1),
		   @NextStepIndex = ISNULL([WS].[tab_index],-1)
	  FROM [dbo].[Workflow_navigazione] [WN]
	  JOIN [dbo].[Workflow_step] [WS] ON [WS].[id] = [WN].[workflow_prossimo_step_id]
	 WHERE [workflow_step_id] = @workflowStepId

	IF(@NextStepId = -1)
	BEGIN
		SELECT TOP(1) 
			   @NextStepId = ISNULL([Id],-1),
			   @NextStepIndex = ISNULL([tab_index],-1)
		  FROM [Procedural].[FC_GetDefaultWorkflowStep](@businessName)
	END

	INSERT INTO [dbo].[richiesta_hst]
			   ([richiesta_id]
			   ,[cliente_id]
			   ,[codice_sala]
			   ,[codice_tir]
			   ,[workflow_step_id]
			   ,[stato_id]
			   ,[stato_sap_id]
			   ,[data_attivazione]
			   ,[categoria_id]
			   ,[presenza_societa_collegate]
			   ,[societa_collegate]
			   ,[presenza_ultimo_bilancio]
			   ,[anno_bilancio]
			   ,[presenza_eventi_negativi]
			   ,[eventi_negativi]
			   ,[presenza_esiti_pregressi]
			   ,[side_letter]
			   ,[canale_contratto_id]
			   ,[canale_sottoscrizione_id]
			   ,[deroga_contratto_id]
			   ,[deroga_merito_user]
			   ,[deroga_merito_date]
			   ,[deroga_merito_id]
			   ,[deroga_merito_nota_id]
			   ,[salvata]
			   ,[work_user_id]
			   ,[work_user]
			   ,[work_date]
			   ,[create_user]
			   ,[create_date]
			   ,[last_mod_user]
			   ,[last_mod_date]
			   ,[ip_address])
		SELECT [id]
			  ,[cliente_id]
			  ,[codice_sala]
			  ,[codice_tir]
			  ,[workflow_step_id]
			  ,[stato_id]
			  ,[stato_sap_id]
			  ,[data_attivazione]
			  ,[categoria_id]
			  ,[presenza_societa_collegate]
			  ,[societa_collegate]
			  ,[presenza_ultimo_bilancio]
			  ,[anno_bilancio]
			  ,[presenza_eventi_negativi]
			  ,[eventi_negativi]
			  ,[presenza_esiti_pregressi]
			  ,[side_letter]
			  ,[canale_contratto_id]
			  ,[canale_sottoscrizione_id]
			  ,[deroga_contratto_id]
			  ,[deroga_merito_user]
			  ,[deroga_merito_date]
			  ,[deroga_merito_id]
			  ,[deroga_merito_nota_id]
			  ,[salvata]
			  ,[work_user_id]
			  ,[work_user]
			  ,[work_date]
			  ,[create_user]
			  ,[create_date]
			  ,[last_mod_user]
			  ,[last_mod_date]
			  ,@ipAddress
		  FROM [dbo].[richiesta]
		 WHERE [id] = @idRichiesta

	UPDATE [dbo].[richiesta]
	   SET [workflow_step_id] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @NextStepId ELSE [workflow_step_id] END
		  ,[categoria_id] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @categoriaId ELSE [categoria_id] END
		  ,[presenza_societa_collegate] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @presenzaSocieta ELSE [presenza_societa_collegate] END
		  ,[societa_collegate] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @societaCollegate ELSE [societa_collegate] END
		  ,[presenza_ultimo_bilancio] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @presenzaBilancio ELSE [presenza_ultimo_bilancio] END
		  ,[anno_bilancio] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @annoBilancio ELSE [anno_bilancio] END
		  ,[presenza_eventi_negativi] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @presenzaEventiNegativi ELSE [presenza_eventi_negativi] END
		  ,[eventi_negativi] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @eventiNegativi ELSE [eventi_negativi] END
		  ,[presenza_esiti_pregressi] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @presenzaEsitiPregressi ELSE [presenza_esiti_pregressi] END
		  ,[last_mod_user] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @utenteUpdate ELSE [last_mod_user] END
		  ,[last_mod_date] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @DataAttuale ELSE [last_mod_date] END
		  ,[work_user_id] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @workUserId ELSE [work_user_id] END
		  ,[work_user] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @utenteUpdate ELSE [work_user] END
		  ,[work_date] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @DataAttuale ELSE [work_date] END
	 WHERE [id] = @idRichiesta

	 SELECT [return_id] = @NextStepId
END
GO
/****** Object:  StoredProcedure [Procedural].[SP_UpdateRichiestaStep2]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 27/01/2022
-- Description:	Salva i valori relativi allo Step2 del processo di Valutazione
-- =============================================
CREATE PROCEDURE [Procedural].[SP_UpdateRichiestaStep2]
	@idRichiesta INT,
	@workflowStepId INT = -1,
	@statoId INT,
	@businessName NVARCHAR(10),
	@notaId INT = -1,
	@noteValutazione NVARCHAR(MAX) = '',
	@ipAddress NVARCHAR(15) = '',
	@workUserId INT = -1,
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @NextStepId INT = -1,
			@NextStepIndex INT = -1,
			@ActualStepIndex INT = ISNULL((
									SELECT [WS].[tab_index]
									  FROM [dbo].[Workflow_step] [WS]
									 WHERE [WS].[id] = @workflowStepId
								),-1);

	DECLARE @NomeStatoRichiesta NVARCHAR(50) = ISNULL((
															SELECT TOP(1) [nome]
															  FROM [dbo].[stato_richiesta]
															  WHERE [id] = @statoId
														),'');

	IF(@NomeStatoRichiesta = 'Negativo' OR @NomeStatoRichiesta = 'Altra Documentazione')
		BEGIN
			SET @NextStepId = @workflowStepId;
			SET @NextStepIndex = @ActualStepIndex;
		END
	ELSE
		BEGIN
			SELECT @NextStepId = ISNULL([WN].[workflow_prossimo_step_id],-1),
				   @NextStepIndex = ISNULL([WS].[tab_index],-1)
			  FROM [dbo].[Workflow_navigazione] [WN]
			  JOIN [dbo].[Workflow_step] [WS] ON [WS].[id] = [WN].[workflow_prossimo_step_id]
			 WHERE [workflow_step_id] = @workflowStepId

			IF(@NextStepId = -1)
			BEGIN
				SELECT TOP(1) 
					   @NextStepId = ISNULL([Id],-1),
					   @NextStepIndex = ISNULL([tab_index],-1)
				  FROM [Procedural].[FC_GetDefaultWorkflowStep](@businessName)
			END
		END

	IF(@notaId = -1 AND LTRIM(RTRIM(@noteValutazione)) != '')
		BEGIN
			-- CREO LA NOTA

			INSERT INTO [dbo].[note_richiesta]
					   ([richiesta_id]
					   ,[workflow_step_id]
					   ,[note]
					   ,[create_user]
					   ,[create_date])
				 VALUES
					   (@idRichiesta
					   ,@workflowStepId
					   ,LTRIM(RTRIM(@noteValutazione))
					   ,@utenteUpdate
					   ,@DataAttuale)

			SET @notaId = SCOPE_IDENTITY();
		END
	ELSE IF(@notaId > 0)
		BEGIN
			-- AGGIORNO LA NOTA

			UPDATE [dbo].[note_richiesta]
			   SET [note] = LTRIM(RTRIM(@noteValutazione))
				  ,[last_mod_user] = @utenteUpdate
				  ,[last_mod_date] = @DataAttuale
			 WHERE [id] = @notaId
		END

	INSERT INTO [dbo].[richiesta_hst]
			   ([richiesta_id]
			   ,[cliente_id]
			   ,[codice_sala]
			   ,[codice_tir]
			   ,[workflow_step_id]
			   ,[stato_id]
			   ,[stato_sap_id]
			   ,[data_attivazione]
			   ,[categoria_id]
			   ,[presenza_societa_collegate]
			   ,[societa_collegate]
			   ,[presenza_ultimo_bilancio]
			   ,[anno_bilancio]
			   ,[presenza_eventi_negativi]
			   ,[eventi_negativi]
			   ,[presenza_esiti_pregressi]
			   ,[side_letter]
			   ,[canale_contratto_id]
			   ,[canale_sottoscrizione_id]
			   ,[deroga_contratto_id]
			   ,[deroga_merito_user]
			   ,[deroga_merito_date]
			   ,[deroga_merito_id]
			   ,[deroga_merito_nota_id]
			   ,[salvata]
			   ,[work_user_id]
			   ,[work_user]
			   ,[work_date]
			   ,[create_user]
			   ,[create_date]
			   ,[last_mod_user]
			   ,[last_mod_date]
			   ,[ip_address])
		SELECT [id]
			  ,[cliente_id]
			  ,[codice_sala]
			  ,[codice_tir]
			  ,[workflow_step_id]
			  ,[stato_id]
			  ,[stato_sap_id]
			  ,[data_attivazione]
			  ,[categoria_id]
			  ,[presenza_societa_collegate]
			  ,[societa_collegate]
			  ,[presenza_ultimo_bilancio]
			  ,[anno_bilancio]
			  ,[presenza_eventi_negativi]
			  ,[eventi_negativi]
			  ,[presenza_esiti_pregressi]
			  ,[side_letter]
			  ,[canale_contratto_id]
			  ,[canale_sottoscrizione_id]
			  ,[deroga_contratto_id]
			  ,[deroga_merito_user]
			  ,[deroga_merito_date]
			  ,[deroga_merito_id]
			  ,[deroga_merito_nota_id]
			  ,[salvata]
			  ,[work_user_id]
			  ,[work_user]
			  ,[work_date]
			  ,[create_user]
			  ,[create_date]
			  ,[last_mod_user]
			  ,[last_mod_date]
			  ,@ipAddress
		  FROM [dbo].[richiesta]
		 WHERE [id] = @idRichiesta

	UPDATE [dbo].[richiesta]
	   SET [workflow_step_id] = CASE WHEN @NextStepIndex >= @ActualStepIndex THEN @NextStepId ELSE [workflow_step_id] END
		  ,[stato_id] = CASE WHEN @NextStepIndex >= @ActualStepIndex THEN @statoId ELSE [stato_id] END
		  ,[last_mod_user] = CASE WHEN @NextStepIndex >= @ActualStepIndex THEN @utenteUpdate ELSE [last_mod_user] END
		  ,[last_mod_date] = CASE WHEN @NextStepIndex >= @ActualStepIndex THEN @DataAttuale ELSE [last_mod_date] END
		  ,[work_user_id] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @workUserId ELSE [work_user_id] END
		  ,[work_user] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @utenteUpdate ELSE [work_user] END
		  ,[work_date] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @DataAttuale ELSE [work_date] END
	 WHERE [id] = @idRichiesta

	 SELECT [return_id] = @NextStepId
END
GO
/****** Object:  StoredProcedure [Procedural].[SP_UpdateRichiestaStep3]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 28/01/2022
-- Description:	Salva i valori relativi allo Step3 del processo di Valutazione
-- =============================================
CREATE PROCEDURE [Procedural].[SP_UpdateRichiestaStep3]
	@idRichiesta INT,
	@workflowStepId INT = -1,
	@businessName NVARCHAR(10),
	@canaleContrattoId INT = NULL,
	@ipAddress NVARCHAR(15) = '',
	@workUserId INT = -1,
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @NextStepId INT = -1,
			@NextStepIndex INT = -1,
			@ActualStepIndex INT = ISNULL((
									SELECT [WS].[tab_index]
									  FROM [dbo].[Workflow_step] [WS]
									 WHERE [WS].[id] = @workflowStepId
								),-1);


	SELECT @NextStepId = ISNULL([WN].[workflow_prossimo_step_id],-1),
		   @NextStepIndex = ISNULL([WS].[tab_index],-1)
	  FROM [dbo].[Workflow_navigazione] [WN]
	  JOIN [dbo].[Workflow_step] [WS] ON [WS].[id] = [WN].[workflow_prossimo_step_id]
	 WHERE [workflow_step_id] = @workflowStepId

	IF(@NextStepId = -1)
	BEGIN
		SELECT TOP(1) 
			   @NextStepId = ISNULL([Id],-1),
			   @NextStepIndex = ISNULL([tab_index],-1)
		  FROM [Procedural].[FC_GetDefaultWorkflowStep](@businessName)
	END

	INSERT INTO [dbo].[richiesta_hst]
			   ([richiesta_id]
			   ,[cliente_id]
			   ,[codice_sala]
			   ,[codice_tir]
			   ,[workflow_step_id]
			   ,[stato_id]
			   ,[stato_sap_id]
			   ,[data_attivazione]
			   ,[categoria_id]
			   ,[presenza_societa_collegate]
			   ,[societa_collegate]
			   ,[presenza_ultimo_bilancio]
			   ,[anno_bilancio]
			   ,[presenza_eventi_negativi]
			   ,[eventi_negativi]
			   ,[presenza_esiti_pregressi]
			   ,[side_letter]
			   ,[canale_contratto_id]
			   ,[canale_sottoscrizione_id]
			   ,[deroga_contratto_id]
			   ,[deroga_merito_user]
			   ,[deroga_merito_date]
			   ,[deroga_merito_id]
			   ,[deroga_merito_nota_id]
			   ,[salvata]
			   ,[work_user_id]
			   ,[work_user]
			   ,[work_date]
			   ,[create_user]
			   ,[create_date]
			   ,[last_mod_user]
			   ,[last_mod_date]
			   ,[ip_address])
		SELECT [id]
			  ,[cliente_id]
			  ,[codice_sala]
			  ,[codice_tir]
			  ,[workflow_step_id]
			  ,[stato_id]
			  ,[stato_sap_id]
			  ,[data_attivazione]
			  ,[categoria_id]
			  ,[presenza_societa_collegate]
			  ,[societa_collegate]
			  ,[presenza_ultimo_bilancio]
			  ,[anno_bilancio]
			  ,[presenza_eventi_negativi]
			  ,[eventi_negativi]
			  ,[presenza_esiti_pregressi]
			  ,[side_letter]
			  ,[canale_contratto_id]
			  ,[canale_sottoscrizione_id]
			  ,[deroga_contratto_id]
			  ,[deroga_merito_user]
			  ,[deroga_merito_date]
			  ,[deroga_merito_id]
			  ,[deroga_merito_nota_id]
			  ,[salvata]
			  ,[work_user_id]
			  ,[work_user]
			  ,[work_date]
			  ,[create_user]
			  ,[create_date]
			  ,[last_mod_user]
			  ,[last_mod_date]
			  ,@ipAddress
		  FROM [dbo].[richiesta]
		 WHERE [id] = @idRichiesta

	UPDATE [dbo].[richiesta]
	   SET [workflow_step_id] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @NextStepId ELSE [workflow_step_id] END
		  ,[canale_contratto_id] = @canaleContrattoId
		  ,[last_mod_user] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @utenteUpdate ELSE [last_mod_user] END
		  ,[last_mod_date] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @DataAttuale ELSE [last_mod_date] END
		  ,[work_user_id] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @workUserId ELSE [work_user_id] END
		  ,[work_user] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @utenteUpdate ELSE [work_user] END
		  ,[work_date] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @DataAttuale ELSE [work_date] END
	 WHERE [id] = @idRichiesta

	 SELECT [return_id] = @NextStepId
END
GO
/****** Object:  StoredProcedure [Procedural].[SP_UpdateRichiestaStep4]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Diego Capone
-- Create date: 28/01/2022
-- Description:	Salva i valori relativi allo Step4 del processo di Valutazione
-- =============================================
CREATE PROCEDURE [Procedural].[SP_UpdateRichiestaStep4]
	@idRichiesta INT,
	@workflowStepId INT = -1,
	@businessName NVARCHAR(10),
	@canaleSottoscrizioneId INT = NULL,
	@ipAddress NVARCHAR(15) = '',
	@workUserId INT = -1,
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @NextStepId INT = -1,
			@NextStepIndex INT = -1,
			@ActualStepIndex INT = ISNULL((
									SELECT [WS].[tab_index]
									  FROM [dbo].[Workflow_step] [WS]
									 WHERE [WS].[id] = @workflowStepId
								),-1);


	SELECT @NextStepId = ISNULL([WN].[workflow_prossimo_step_id],-1),
		   @NextStepIndex = ISNULL([WS].[tab_index],-1)
	  FROM [dbo].[Workflow_navigazione] [WN]
	  JOIN [dbo].[Workflow_step] [WS] ON [WS].[id] = [WN].[workflow_prossimo_step_id]
	 WHERE [workflow_step_id] = @workflowStepId

	IF(@NextStepId = -1)
	BEGIN
		SELECT TOP(1) 
			   @NextStepId = ISNULL([Id],-1),
			   @NextStepIndex = ISNULL([tab_index],-1)
		  FROM [Procedural].[FC_GetDefaultWorkflowStep](@businessName)
	END

	INSERT INTO [dbo].[richiesta_hst]
			   ([richiesta_id]
			   ,[cliente_id]
			   ,[codice_sala]
			   ,[codice_tir]
			   ,[workflow_step_id]
			   ,[stato_id]
			   ,[stato_sap_id]
			   ,[data_attivazione]
			   ,[categoria_id]
			   ,[presenza_societa_collegate]
			   ,[societa_collegate]
			   ,[presenza_ultimo_bilancio]
			   ,[anno_bilancio]
			   ,[presenza_eventi_negativi]
			   ,[eventi_negativi]
			   ,[presenza_esiti_pregressi]
			   ,[side_letter]
			   ,[canale_contratto_id]
			   ,[canale_sottoscrizione_id]
			   ,[deroga_contratto_id]
			   ,[deroga_merito_user]
			   ,[deroga_merito_date]
			   ,[deroga_merito_id]
			   ,[deroga_merito_nota_id]
			   ,[salvata]
			   ,[work_user_id]
			   ,[work_user]
			   ,[work_date]
			   ,[create_user]
			   ,[create_date]
			   ,[last_mod_user]
			   ,[last_mod_date]
			   ,[ip_address])
		SELECT [id]
			  ,[cliente_id]
			  ,[codice_sala]
			  ,[codice_tir]
			  ,[workflow_step_id]
			  ,[stato_id]
			  ,[stato_sap_id]
			  ,[data_attivazione]
			  ,[categoria_id]
			  ,[presenza_societa_collegate]
			  ,[societa_collegate]
			  ,[presenza_ultimo_bilancio]
			  ,[anno_bilancio]
			  ,[presenza_eventi_negativi]
			  ,[eventi_negativi]
			  ,[presenza_esiti_pregressi]
			  ,[side_letter]
			  ,[canale_contratto_id]
			  ,[canale_sottoscrizione_id]
			  ,[deroga_contratto_id]
			  ,[deroga_merito_user]
			  ,[deroga_merito_date]
			  ,[deroga_merito_id]
			  ,[deroga_merito_nota_id]
			  ,[salvata]
			  ,[work_user_id]
			  ,[work_user]
			  ,[work_date]
			  ,[create_user]
			  ,[create_date]
			  ,[last_mod_user]
			  ,[last_mod_date]
			  ,@ipAddress
		  FROM [dbo].[richiesta]
		 WHERE [id] = @idRichiesta

	UPDATE [dbo].[richiesta]
	   SET [workflow_step_id] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @NextStepId ELSE [workflow_step_id] END
		  ,[canale_sottoscrizione_id] = @canaleSottoscrizioneId
		  ,[last_mod_user] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @utenteUpdate ELSE [last_mod_user] END
		  ,[last_mod_date] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @DataAttuale ELSE [last_mod_date] END
		  ,[work_user_id] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @workUserId ELSE [work_user_id] END
		  ,[work_user] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @utenteUpdate ELSE [work_user] END
		  ,[work_date] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @DataAttuale ELSE [work_date] END
	 WHERE [id] = @idRichiesta

	 SELECT [return_id] = @NextStepId
END
GO
/****** Object:  StoredProcedure [Procedural].[SP_UpdateRichiestaStep5]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Diego Capone
-- Create date: 28/01/2022
-- Description:	Salva i valori relativi allo Step5 del processo di Valutazione
-- =============================================
CREATE PROCEDURE [Procedural].[SP_UpdateRichiestaStep5]
	@idRichiesta INT,
	@workflowStepId INT = -1,
	@sideLetter BIT,
	@businessName NVARCHAR(10),
	@ipAddress NVARCHAR(15) = '',
	@workUserId INT = -1,
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @NextStepId INT = -1,
			@NextStepIndex INT = -1,
			@ActualStepIndex INT = ISNULL((
									SELECT TOP(1) [WS].[tab_index]
									  FROM [dbo].[Workflow_step] [WS]
									 WHERE [WS].[id] = @workflowStepId
								),-1);


	SELECT @NextStepId = ISNULL([WN].[workflow_prossimo_step_id],@workflowStepId),
		   @NextStepIndex = ISNULL([WS].[tab_index],6)
	  FROM [dbo].[Workflow_navigazione] [WN]
	  JOIN [dbo].[Workflow_step] [WS] ON [WS].[id] = [WN].[workflow_prossimo_step_id]
	 WHERE [workflow_step_id] = @workflowStepId

	IF(@NextStepId = -1)
	BEGIN
		SELECT TOP(1) 
			   @NextStepId = ISNULL([Id],-1),
			   @NextStepIndex = ISNULL([tab_index],-1)
		  FROM [Procedural].[FC_GetDefaultWorkflowStep](@businessName)
	END

	INSERT INTO [dbo].[richiesta_hst]
			   ([richiesta_id]
			   ,[cliente_id]
			   ,[codice_sala]
			   ,[codice_tir]
			   ,[workflow_step_id]
			   ,[stato_id]
			   ,[stato_sap_id]
			   ,[data_attivazione]
			   ,[categoria_id]
			   ,[presenza_societa_collegate]
			   ,[societa_collegate]
			   ,[presenza_ultimo_bilancio]
			   ,[anno_bilancio]
			   ,[presenza_eventi_negativi]
			   ,[eventi_negativi]
			   ,[presenza_esiti_pregressi]
			   ,[side_letter]
			   ,[canale_contratto_id]
			   ,[canale_sottoscrizione_id]
			   ,[deroga_contratto_id]
			   ,[deroga_merito_user]
			   ,[deroga_merito_date]
			   ,[deroga_merito_id]
			   ,[deroga_merito_nota_id]
			   ,[salvata]
			   ,[work_user_id]
			   ,[work_user]
			   ,[work_date]
			   ,[create_user]
			   ,[create_date]
			   ,[last_mod_user]
			   ,[last_mod_date]
			   ,[ip_address])
		SELECT [id]
			  ,[cliente_id]
			  ,[codice_sala]
			  ,[codice_tir]
			  ,[workflow_step_id]
			  ,[stato_id]
			  ,[stato_sap_id]
			  ,[data_attivazione]
			  ,[categoria_id]
			  ,[presenza_societa_collegate]
			  ,[societa_collegate]
			  ,[presenza_ultimo_bilancio]
			  ,[anno_bilancio]
			  ,[presenza_eventi_negativi]
			  ,[eventi_negativi]
			  ,[presenza_esiti_pregressi]
			  ,[side_letter]
			  ,[canale_contratto_id]
			  ,[canale_sottoscrizione_id]
			  ,[deroga_contratto_id]
			  ,[deroga_merito_user]
			  ,[deroga_merito_date]
			  ,[deroga_merito_id]
			  ,[deroga_merito_nota_id]
			  ,[salvata]
			  ,[work_user_id]
			  ,[work_user]
			  ,[work_date]
			  ,[create_user]
			  ,[create_date]
			  ,[last_mod_user]
			  ,[last_mod_date]
			  ,@ipAddress
		  FROM [dbo].[richiesta]
		 WHERE [id] = @idRichiesta

	DECLARE @StatoId INT = ISNULL((
				SELECT TOP(1) [id]
				  FROM [dbo].[stato_richiesta]
				 WHERE [nome] = 'Attivo'
		),-1);

	UPDATE [dbo].[richiesta]
	   SET [workflow_step_id] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @NextStepId ELSE [workflow_step_id] END
		  ,[side_letter] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @sideLetter ELSE [side_letter] END
		  ,[last_mod_user] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @utenteUpdate ELSE [last_mod_user] END
		  ,[last_mod_date] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @DataAttuale ELSE [last_mod_date] END
		  ,[salvata] = 1
		  ,[stato_id] = CASE WHEN @StatoId > 0 THEN @StatoId ELSE [stato_id] END
		  ,[work_user_id] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @workUserId ELSE [work_user_id] END
		  ,[work_user] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @utenteUpdate ELSE [work_user] END
		  ,[work_date] = CASE WHEN @NextStepIndex > @ActualStepIndex THEN @DataAttuale ELSE [work_date] END
	 WHERE [id] = @idRichiesta

	 SELECT [return_id] = @NextStepId
END
GO
/****** Object:  StoredProcedure [Procedural].[SP_UpdateRichiestaStepByCodice]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 04/03/2022
-- Description:	Partendo da codice cliente e business porta una richiesta ad uno step
-- =============================================
CREATE PROCEDURE [Procedural].[SP_UpdateRichiestaStepByCodice]
	@codiceCliente NVARCHAR(50),
	@workflowStepIndex INT = -1,
	@businessName NVARCHAR(10),
	@codiceSala NVARCHAR(50) = '',
	@codiceTir NVARCHAR(50) = '',
	@ipAddress NVARCHAR(50) = '',
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @idRichiesta INT = ISNULL((
										SELECT TOP(1) [R].[id]
											FROM [dbo].[richiesta] [R]
											JOIN [dbo].[cliente] [C] ON [C].[id] = [R].[cliente_id]
											JOIN [Anagraphics].[Cliente_Business] [C_B] ON [C_B].[cliente_id] = [R].[cliente_id]
											JOIN [Anagraphics].[Business] [B] ON [B].[id] = [C_B].[business_id]
										   WHERE [C].[codice_cliente] = @codiceCliente
											 AND [B].[name] = @businessName
									),-1);

    IF(@idRichiesta > 0)
	BEGIN
	   EXEC [Procedural].[SP_UpdateRichiestaValutazioneStep]
			@idRichiesta = @idRichiesta,
			@workflowStepIndex = @workflowStepIndex,
			@businessName = @businessName,
			@codiceSala = @codiceSala,
			@codiceTir = @codiceTir,
			@ipAddress = @ipAddress,
			@utenteUpdate = @utenteUpdate
	END

	 SELECT [return_id] = @idRichiesta
END
GO
/****** Object:  StoredProcedure [Procedural].[SP_UpdateRichiestaValutazioneStep]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Diego Capone
-- Create date: 28/01/2022
-- Description:	Imposta una richiesta ad uno specifico step index
-- =============================================
CREATE PROCEDURE [Procedural].[SP_UpdateRichiestaValutazioneStep]
	@idRichiesta INT,
	@workflowStepIndex INT = -1,
	@businessName NVARCHAR(10),
	@codiceSala NVARCHAR(50) = '',
	@codiceTir NVARCHAR(50) = '',
	@ipAddress NVARCHAR(50) = '',
	@utenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();

	DECLARE @NextStepId INT = ISNULL((
									SELECT TOP(1) [W_S].[id]
									  FROM [dbo].[Workflow_step] [W_S]
									  JOIN [dbo].[Workflow] [W] ON [W_S].[workflow_id] = [W].[id]
									 WHERE [W_S].[tab_index] = @workflowStepIndex
									   AND [W].[business] = @businessName
								),-1);

	INSERT INTO [dbo].[richiesta_hst]
			   ([richiesta_id]
			   ,[cliente_id]
			   ,[codice_sala]
			   ,[codice_tir]
			   ,[workflow_step_id]
			   ,[stato_id]
			   ,[stato_sap_id]
			   ,[data_attivazione]
			   ,[categoria_id]
			   ,[presenza_societa_collegate]
			   ,[societa_collegate]
			   ,[presenza_ultimo_bilancio]
			   ,[anno_bilancio]
			   ,[presenza_eventi_negativi]
			   ,[eventi_negativi]
			   ,[presenza_esiti_pregressi]
			   ,[side_letter]
			   ,[canale_contratto_id]
			   ,[canale_sottoscrizione_id]
			   ,[deroga_contratto_id]
			   ,[deroga_merito_user]
			   ,[deroga_merito_date]
			   ,[deroga_merito_id]
			   ,[deroga_merito_nota_id]
			   ,[salvata]
			   ,[work_user_id]
			   ,[work_user]
			   ,[work_date]
			   ,[create_user]
			   ,[create_date]
			   ,[last_mod_user]
			   ,[last_mod_date]
			   ,[ip_address])
		SELECT [id]
			  ,[cliente_id]
			  ,[codice_sala]
			  ,[codice_tir]
			  ,[workflow_step_id]
			  ,[stato_id]
			  ,[stato_sap_id]
			  ,[data_attivazione]
			  ,[categoria_id]
			  ,[presenza_societa_collegate]
			  ,[societa_collegate]
			  ,[presenza_ultimo_bilancio]
			  ,[anno_bilancio]
			  ,[presenza_eventi_negativi]
			  ,[eventi_negativi]
			  ,[presenza_esiti_pregressi]
			  ,[side_letter]
			  ,[canale_contratto_id]
			  ,[canale_sottoscrizione_id]
			  ,[deroga_contratto_id]
			  ,[deroga_merito_user]
			  ,[deroga_merito_date]
			  ,[deroga_merito_id]
			  ,[deroga_merito_nota_id]
			  ,[salvata]
			  ,[work_user_id]
			  ,[work_user]
			  ,[work_date]
			  ,[create_user]
			  ,[create_date]
			  ,[last_mod_user]
			  ,[last_mod_date]
			  ,@ipAddress
		  FROM [dbo].[richiesta]
		 WHERE [id] = @idRichiesta

	DECLARE @StatoId INT = ISNULL((
				SELECT TOP(1) [id]
				  FROM [dbo].[stato_richiesta]
				 WHERE [nome] = 'Positivo'
		),-1);

	UPDATE [dbo].[richiesta]
	   SET [workflow_step_id] = @NextStepId,
		   [stato_id] = @StatoId,
		   [codice_sala] = CASE WHEN @codiceSala != '' THEN @codiceSala ELSE [codice_sala] END,
		   [codice_tir] = CASE WHEN @codiceTir != '' THEN @codiceTir ELSE [codice_tir] END
	 WHERE [id] = @idRichiesta

	 SELECT [return_id] = @idRichiesta
END
GO
/****** Object:  StoredProcedure [Statistics].[GetNuoviProspect]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 14/02/2022
-- Description:	Carica i dati per il popolamento del grafico Dashboard
-- =============================================
CREATE PROCEDURE [Statistics].[GetNuoviProspect]
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	SET datefirst 1;

	DECLARE @DateRef DATETIME = GETDATE();
    DECLARE @DateLastWeek DATETIME = DATEADD(day, -7, @DateRef);
			
	DECLARE @DateRefWeek INT = DATEPART(WEEK, @DateRef),
			@DateRefYear INT = DATEPART(YEAR, @DateRef);
			
	DECLARE @DateRefWeek_Prec INT = DATEPART(WEEK, @DateLastWeek),
			@DateRefYear_Prec INT = DATEPART(YEAR, @DateLastWeek);

	DECLARE @TotaleLastWeek INT = ISNULL((
											SELECT [totale]
											  FROM [Statistics].[NuoviProspect]
											 WHERE [anno] = @DateRefYear_Prec
											   AND [settimana] = @DateRefWeek_Prec
										),0);

	IF EXISTS (
				SELECT [id]
				  FROM [Statistics].[NuoviProspect]
				 WHERE [anno] = @DateRefYear
				   AND [settimana] = @DateRefWeek
			  )
			  BEGIN
					SELECT [id]
						  ,[anno]
						  ,[settimana]
						  ,[lunedi]
						  ,[martedi]
						  ,[mercoledi]
						  ,[giovedi]
						  ,[venerdi]
						  ,[sabato]
						  ,[domenica]
						  ,[totale]				  
						  ,[aumento] = CASE WHEN @TotaleLastWeek = 0 
											THEN [totale] 
											ELSE CAST(ROUND((((CAST([totale] AS decimal)-CAST(@TotaleLastWeek AS decimal))/CAST(@TotaleLastWeek AS decimal))*100),0) AS int)
										END
						  ,[create_user]
						  ,[create_date]
						  ,[last_mod_user]
						  ,[last_mod_date]
					  FROM [Statistics].[NuoviProspect]
					 WHERE [anno] = @DateRefYear
					   AND [settimana] = @DateRefWeek
				END
			ELSE
				BEGIN
					SELECT [id] = 0
						  ,[anno] = @DateRefYear
						  ,[settimana] = @DateRefWeek
						  ,[lunedi] = 0
						  ,[martedi] = 0
						  ,[mercoledi] = 0
						  ,[giovedi] = 0
						  ,[venerdi] = 0
						  ,[sabato] = 0
						  ,[domenica] = 0
						  ,[totale] = 0			  
						  ,[aumento] = CASE WHEN @TotaleLastWeek = 0 
											THEN 0 
											ELSE CAST(ROUND((((CAST(0 AS decimal)-CAST(@TotaleLastWeek AS decimal))/CAST(@TotaleLastWeek AS decimal))*100),0) AS int)
										END
						  ,[create_user] = ''
						  ,[create_date] = NULL
						  ,[last_mod_user] = ''
						  ,[last_mod_date] = NULL
				END
END
GO
/****** Object:  StoredProcedure [Statistics].[SP_CreateUpdateStatoGaranzie]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 17/02/2022
-- Description:	Inserisce un record nella tabella [Statistics].[StatoScadenzaGaranzia]
-- =============================================
CREATE PROCEDURE [Statistics].[SP_CreateUpdateStatoGaranzie]
	@IdGaranzia INT = -1,
	@IdRichiesta INT = -1,
	@CodiceGaranzia NVARCHAR(50),
	@InScadenza BIT,
	@Scaduta BIT,
	@UtenteUpdate NVARCHAR(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();
	DECLARE @ReturnedId INT = -1;

	SET @IdGaranzia = ISNULL((
								SELECT TOP(1) [id]
								  FROM [dbo].[garanzie]
								 WHERE [codice_garanzia] = @CodiceGaranzia
								   AND [richiesta_id] = @IdRichiesta
							),-1);

	IF(@IdGaranzia > 0)
	BEGIN
		SET @ReturnedId = ISNULL((
									SELECT [id]
									  FROM [Statistics].[StatoScadenzaGaranzia]
									 WHERE [garanzia_id] = @IdGaranzia
								),-1);
		IF (@ReturnedId > 0)
				BEGIN
					UPDATE [Statistics].[StatoScadenzaGaranzia]
					   SET [in_scadenza] = @InScadenza
						  ,[scaduta] = @Scaduta
						  ,[alert_scadenza] = CASE WHEN @InScadenza = 1 THEN @DataAttuale ELSE [alert_scadenza] END
						  ,[alert_scaduta] = CASE WHEN @Scaduta = 1 THEN @DataAttuale ELSE [alert_scaduta] END
						  ,[last_mod_user] = @UtenteUpdate
						  ,[last_mod_date] = @DataAttuale
					 WHERE [garanzia_id] = @IdGaranzia
				END
			ELSE
				BEGIN

					INSERT INTO [Statistics].[StatoScadenzaGaranzia]
							   ([garanzia_id]
							   ,[in_scadenza]
							   ,[scaduta]
							   ,[alert_scadenza]
							   ,[alert_scaduta]
							   ,[create_user]
							   ,[create_date])
						 VALUES
							   (@IdGaranzia
							   ,@InScadenza
							   ,@Scaduta
							   ,CASE WHEN @InScadenza = 1 THEN @DataAttuale ELSE NULL END
							   ,CASE WHEN @Scaduta = 1 THEN @DataAttuale ELSE NULL END
							   ,@UtenteUpdate
							   ,@DataAttuale)

					SET @ReturnedId = SCOPE_IDENTITY();
				END
	END

	SELECT *
	  FROM [Statistics].[StatoScadenzaGaranzia]
	 WHERE [id] = @ReturnedId
END
GO
/****** Object:  StoredProcedure [Statistics].[SP_GetClientiAttiviByRole]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 04/04/2022
-- Description:	Carica i dati per il popolamento del grafico Dashboard
-- =============================================
CREATE PROCEDURE [Statistics].[SP_GetClientiAttiviByRole]
	@idRole INT,
	@isAdmin BIT,
	@anno INT,
	@selectedBusiness NVARCHAR(100) = ''''''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	CREATE TABLE #AvaiableBusiness ([business] NVARCHAR(10) NOT NULL);
	INSERT #AvaiableBusiness ([business])
	SELECT DISTINCT [W].[business]
			   FROM [dbo].[workflow_step_roles] [W_S_R]
			   JOIN [dbo].[Workflow_step] [W_S] ON [W_S_R].[workflow_step_id] = [W_S].[id]
			   JOIN [dbo].[Workflow] [W] ON [W].[id] = [W_S].[workflow_id]
			  WHERE [role_id] = @idRole

    DECLARE @sql NVARCHAR(MAX) = '
	SELECT [id] = MAX([id])
		  ,[anno]
		  ,[mese]
		  ,[clienti_attivi] = SUM([clienti_attivi])
		  ,[giorno_aggiornamento] = MAX([giorno_aggiornamento])
		  ,[create_user] = MAX([create_user])
		  ,[create_date] = MAX([create_date])
		  ,[last_mod_user] = MAX([last_mod_user])
		  ,[last_mod_date] = MAX([last_mod_date])
		  FROM [Statistics].[ClientiAttivi]
		 WHERE [anno] = @anno
		   AND (
					@isAdmin = 1 OR
					[business] IN (
						SELECT [business] FROM #AvaiableBusiness
					)
				)
		   AND [business] IN (' + @selectedBusiness + ') 
	  GROUP BY [anno],[mese]';

		  --SELECT @sql

	DECLARE @params NVARCHAR(4000) = '@isAdmin BIT, @anno INT';

	EXEC sp_executesql @sql, @params, @isAdmin = @isAdmin, @anno = @anno
END
GO
/****** Object:  StoredProcedure [Statistics].[SP_GetGaranzieScadenzaMese]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 18/02/2022
-- Description:	Carica la lista delle scadenze giorno per giorno di un mese
-- =============================================
CREATE PROCEDURE [Statistics].[SP_GetGaranzieScadenzaMese]
	@Anno INT,
	@Mese INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	SELECT @Anno AS anno
		  ,@Mese AS mese
		  ,DAY([create_date]) AS giorno
		  ,COUNT([id]) AS numero_scadenze
		  ,MAX([create_date]) AS last_date
	  FROM [Statistics].[StatoScadenzaGaranzia]
	 WHERE DATEPART(YEAR, [create_date]) = @Anno
	   AND DATEPART(MONTH, [create_date]) = @Mese
	   AND ISNULL([in_scadenza],0) = 1
	   AND ISNULL([scaduta],0) = 0
  GROUP BY DAY([create_date])

END
GO
/****** Object:  StoredProcedure [Statistics].[SP_GetGaranzieScadenzaMeseByRole]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 18/02/2022
-- Description:	Carica la lista delle scadenze giorno per giorno di un mese
-- =============================================
CREATE PROCEDURE [Statistics].[SP_GetGaranzieScadenzaMeseByRole]
	@Anno INT,
	@Mese INT,
	@idRole INT,
	@isAdmin BIT,
	@selectedBusiness NVARCHAR(100) = ''''''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	CREATE TABLE #AvaiableBusiness ([business] NVARCHAR(10) NOT NULL);
	INSERT #AvaiableBusiness ([business])
	SELECT DISTINCT [W].[business]
			   FROM [dbo].[workflow_step_roles] [W_S_R]
			   JOIN [dbo].[Workflow_step] [W_S] ON [W_S_R].[workflow_step_id] = [W_S].[id]
			   JOIN [dbo].[Workflow] [W] ON [W].[id] = [W_S].[workflow_id]
			  WHERE [role_id] = @idRole

	DECLARE @sql NVARCHAR(MAX) = '
	SELECT @Anno AS anno
		  ,@Mese AS mese
		  ,DAY([S_S_G].[create_date]) AS giorno
		  ,COUNT([S_S_G].[id]) AS numero_scadenze
		  ,MAX([S_S_G].[create_date]) AS last_date
	  FROM [Statistics].[StatoScadenzaGaranzia] [S_S_G]
	  JOIN [dbo].[garanzie] [G] ON [G].[id] = [S_S_G].[garanzia_id]
	  JOIN [dbo].[richiesta] [R] ON [R].[id] = [G].[richiesta_id]
	  JOIN [Anagraphics].[Cliente_Business] [C_B] ON [C_B].[cliente_id] = [R].[cliente_id]
	  JOIN [Anagraphics].[Business] [B] ON [B].[id] = [C_B].[business_id]
	 WHERE DATEPART(YEAR, [S_S_G].[create_date]) = @Anno
	   AND DATEPART(MONTH, [S_S_G].[create_date]) = @Mese
	   AND ISNULL([in_scadenza],0) = 1
	   AND ISNULL([scaduta],0) = 0
	   AND (
				@isAdmin = 1 OR
				[B].[name] IN (
					SELECT [business] FROM #AvaiableBusiness
				)
			)
	   AND [B].[name] IN (' + @selectedBusiness + ') 
  GROUP BY DAY([S_S_G].[create_date])';

		  --SELECT @sql

	DECLARE @params NVARCHAR(4000) = '@isAdmin BIT, @Anno INT, @Mese INT';

	EXEC sp_executesql @sql, @params, @isAdmin = @isAdmin, @Anno = @Anno, @Mese = @Mese
END
GO
/****** Object:  StoredProcedure [Statistics].[SP_GetNuoviProspect]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 14/02/2022
-- Description:	Carica i dati per il popolamento del grafico Dashboard
-- =============================================
CREATE PROCEDURE [Statistics].[SP_GetNuoviProspect]
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	SET datefirst 1;

	DECLARE @DateRef DATETIME = GETDATE();
    DECLARE @DateLastWeek DATETIME = DATEADD(day, -7, @DateRef);
			
	DECLARE @DateRefWeek INT = DATEPART(WEEK, @DateRef),
			@DateRefYear INT = DATEPART(YEAR, @DateRef);
			
	DECLARE @DateRefWeek_Prec INT = DATEPART(WEEK, @DateLastWeek),
			@DateRefYear_Prec INT = DATEPART(YEAR, @DateLastWeek);

	DECLARE @TotaleLastWeek INT = ISNULL((
											SELECT SUM([totale])
											  FROM [Statistics].[NuoviProspect]
											 WHERE [anno] = @DateRefYear_Prec
											   AND [settimana] = @DateRefWeek_Prec
										),0);

	DECLARE @LastNullDate DATETIME = ISNULL((
												SELECT TOP(1) ISNULL([last_mod_date],[create_date])
												  FROM [Statistics].[NuoviProspect]
											  ORDER BY [anno] DESC, [settimana] DESC
											), GETDATE());

	IF EXISTS (
				SELECT [id]
				  FROM [Statistics].[NuoviProspect]
				 WHERE [anno] = @DateRefYear
				   AND [settimana] = @DateRefWeek
			  )
			  BEGIN
					SELECT [id]
						  ,[anno]
						  ,[settimana]
						  ,[lunedi]
						  ,[martedi]
						  ,[mercoledi]
						  ,[giovedi]
						  ,[venerdi]
						  ,[sabato]
						  ,[domenica]
						  ,[totale]				  
						  ,[aumento] = CASE WHEN @TotaleLastWeek = 0 
											THEN [totale] 
											ELSE CAST(ROUND((((CAST([totale] AS decimal)-CAST(@TotaleLastWeek AS decimal))/CAST(@TotaleLastWeek AS decimal))*100),0) AS int)
										END
						  ,[create_user]
						  ,[create_date]
						  ,[last_mod_user]
						  ,[last_mod_date]
					  FROM [Statistics].[NuoviProspect]
					 WHERE [anno] = @DateRefYear
					   AND [settimana] = @DateRefWeek
				END
			ELSE
				BEGIN
					SELECT [id] = 0
						  ,[anno] = @DateRefYear
						  ,[settimana] = @DateRefWeek
						  ,[lunedi] = 0
						  ,[martedi] = 0
						  ,[mercoledi] = 0
						  ,[giovedi] = 0
						  ,[venerdi] = 0
						  ,[sabato] = 0
						  ,[domenica] = 0
						  ,[totale] = 0			  
						  ,[aumento] = CASE WHEN @TotaleLastWeek = 0 
											THEN 0 
											ELSE CAST(ROUND((((CAST(0 AS decimal)-CAST(@TotaleLastWeek AS decimal))/CAST(@TotaleLastWeek AS decimal))*100),0) AS int)
										END
						  ,[create_user] = ''
						  ,[create_date] = @LastNullDate
						  ,[last_mod_user] = ''
						  ,[last_mod_date] = @LastNullDate
				END
END
GO
/****** Object:  StoredProcedure [Statistics].[SP_GetNuoviProspectByRole]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 14/02/2022
-- Description:	Carica i dati per il popolamento del grafico Dashboard
-- =============================================
CREATE PROCEDURE [Statistics].[SP_GetNuoviProspectByRole]
	@idRole INT,
	@isAdmin BIT,
	@selectedBusiness NVARCHAR(100) = ''''''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	SET datefirst 1;

	DECLARE @DateRef DATETIME = GETDATE();
    DECLARE @DateLastWeek DATETIME = DATEADD(day, -7, @DateRef);
			
	DECLARE @DateRefWeek INT = DATEPART(WEEK, @DateRef),
			@DateRefYear INT = DATEPART(YEAR, @DateRef);
			
	DECLARE @DateRefWeek_Prec INT = DATEPART(WEEK, @DateLastWeek),
			@DateRefYear_Prec INT = DATEPART(YEAR, @DateLastWeek);

	CREATE TABLE #AvaiableBusiness ([business] NVARCHAR(10) NOT NULL);
	INSERT #AvaiableBusiness ([business])
	SELECT DISTINCT [W].[business]
			   FROM [dbo].[workflow_step_roles] [W_S_R]
			   JOIN [dbo].[Workflow_step] [W_S] ON [W_S_R].[workflow_step_id] = [W_S].[id]
			   JOIN [dbo].[Workflow] [W] ON [W].[id] = [W_S].[workflow_id]
			  WHERE [role_id] = @idRole

	DECLARE @sql NVARCHAR(MAX) = '
	DECLARE @TotaleLastWeek INT = ISNULL((
											SELECT SUM([totale])
											  FROM [Statistics].[NuoviProspect]
											 WHERE [anno] = @DateRefYear_Prec
											   AND [settimana] = @DateRefWeek_Prec
											   AND (
													   @isAdmin = 1 OR
													   [business] IN (
															SELECT [business] FROM #AvaiableBusiness
														)
													)
											   AND [business] IN (' + @selectedBusiness + ') 
										),0);

	DECLARE @LastNullDate DATETIME = ISNULL((
												SELECT TOP(1) ISNULL([last_mod_date],[create_date])
												  FROM [Statistics].[NuoviProspect]
											     WHERE [business] IN (' + @selectedBusiness + ') 
											  ORDER BY [anno] DESC, [settimana] DESC
											), GETDATE());

	IF EXISTS (
				SELECT [id]
				  FROM [Statistics].[NuoviProspect]
				 WHERE [anno] = @DateRefYear
				   AND [settimana] = @DateRefWeek
				   AND (
						   @isAdmin = 1 OR
						   [business] IN (
								SELECT [business] FROM #AvaiableBusiness
							)
						)
					AND [business] IN (' + @selectedBusiness + ') 
			)
			  BEGIN
					SELECT [id] = MAX([id])
						  ,[anno]
						  ,[settimana]
						  ,[lunedi] = SUM([lunedi])
						  ,[martedi] = SUM([martedi])
						  ,[mercoledi] = SUM([mercoledi])
						  ,[giovedi] = SUM([giovedi])
						  ,[venerdi] = SUM([venerdi])
						  ,[sabato] = SUM([sabato])
						  ,[domenica] = SUM([domenica])
						  ,[totale] = SUM([totale])
						  ,[aumento] = CASE WHEN @TotaleLastWeek = 0 
											THEN SUM([totale])
											ELSE CAST(ROUND((((CAST(SUM([totale]) AS decimal)-CAST(@TotaleLastWeek AS decimal))/CAST(@TotaleLastWeek AS decimal))*100),0) AS int)
										END
						  ,[create_user] = MAX([create_user])
						  ,[create_date] = MAX([create_date])
						  ,[last_mod_user] = MAX([last_mod_user])
						  ,[last_mod_date] = MAX([last_mod_date])
					  FROM [Statistics].[NuoviProspect]
					 WHERE [anno] = @DateRefYear
					   AND [settimana] = @DateRefWeek
					   AND (
							   @isAdmin = 1 OR
							   [business] IN (
									SELECT [business] FROM #AvaiableBusiness
								)
							)
						AND [business] IN (' + @selectedBusiness + ') 
				  GROUP BY [anno], [settimana]
				END
			ELSE
				BEGIN
					SELECT [id] = 0
						  ,[anno] = @DateRefYear
						  ,[settimana] = @DateRefWeek
						  ,[lunedi] = 0
						  ,[martedi] = 0
						  ,[mercoledi] = 0
						  ,[giovedi] = 0
						  ,[venerdi] = 0
						  ,[sabato] = 0
						  ,[domenica] = 0
						  ,[totale] = 0			  
						  ,[aumento] = CASE WHEN @TotaleLastWeek = 0 
											THEN 0 
											ELSE CAST(ROUND((((CAST(0 AS decimal)-CAST(@TotaleLastWeek AS decimal))/CAST(@TotaleLastWeek AS decimal))*100),0) AS int)
										END
						  ,[create_user] = ''''
						  ,[create_date] = @LastNullDate
						  ,[last_mod_user] = ''''
						  ,[last_mod_date] = @LastNullDate
				END';

		  --SELECT @sql

	DECLARE @params NVARCHAR(4000) = '@isAdmin BIT, @DateRefYear_Prec INT, @DateRefWeek_Prec INT, @DateRefYear INT, @DateRefWeek INT';

	EXEC sp_executesql @sql, @params, @isAdmin = @isAdmin, @DateRefYear_Prec = @DateRefYear_Prec, @DateRefWeek_Prec = @DateRefWeek_Prec, @DateRefYear = @DateRefYear, @DateRefWeek = @DateRefWeek
END
GO
/****** Object:  StoredProcedure [Statistics].[SP_UpdateClientiAttivi]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 14/02/2022
-- Description:	Inserisce un record nella tabella Clienti Attivi relativa ad uno specifico mese
-- =============================================
CREATE PROCEDURE [Statistics].[SP_UpdateClientiAttivi]
	@DateGap INT,
	@BusinessName NVARCHAR(50) = '',
	@UtenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @DataAttuale DATETIME = GETDATE();
    DECLARE @DateRef DATETIME = DATEADD(day, @DateGap, @DataAttuale);
			
	DECLARE @DateRefMonth INT = DATEPART(MONTH, @DateRef),
			@DateRefYear INT = DATEPART(YEAR, @DateRef),
			@DateRefDay INT = DAY(@DateRef);

	DECLARE @CountForMonth INT = ISNULL((
										SELECT COUNT([R].[id])
										  FROM [dbo].[richiesta] [R]
										  JOIN [Anagraphics].[Cliente_Business] [C_B] ON [R].[cliente_id] = [C_B].[cliente_id]
										  JOIN [Anagraphics].[Business] [B] ON [B].[id] = [C_B].[business_id]
										 WHERE DATEPART(MONTH,[R].[data_attivazione]) = @DateRefMonth
										   AND DATEPART(YEAR, [R].[data_attivazione]) = @DateRefYear
										   AND [data_attivazione] IS NOT NULL
										   AND (
													@BusinessName = '' OR
													(
														@BusinessName != '' AND
														[B].[name] = @BusinessName
													)
												)
									),0);

	IF EXISTS (

				SELECT [id]
				  FROM [Statistics].[ClientiAttivi]
				 WHERE [anno] = @DateRefYear
				   AND [mese] = @DateRefMonth
				   AND [business] = @BusinessName
			)
			BEGIN
				UPDATE [Statistics].[ClientiAttivi]
				   SET [clienti_attivi] = @CountForMonth
					  ,[giorno_aggiornamento] = @DateRefDay
					  ,[last_mod_user] = @UtenteUpdate
					  ,[last_mod_date] = @DataAttuale
				 WHERE [anno] = @DateRefYear
				   AND [mese] = @DateRefMonth
				   AND [business] = @BusinessName
			END
		ELSE
			BEGIN
				INSERT INTO [Statistics].[ClientiAttivi]
						   ([anno]
						   ,[mese]
						   ,[business]
						   ,[clienti_attivi]
						   ,[giorno_aggiornamento]
						   ,[create_user]
						   ,[create_date])
					 VALUES
						   (@DateRefYear
						   ,@DateRefMonth
						   ,@BusinessName
						   ,@CountForMonth
						   ,@DateRefDay
						   ,@UtenteUpdate
						   ,@DataAttuale)
			END    
END
GO
/****** Object:  StoredProcedure [Statistics].[SP_UpdateNuoviProspect]    Script Date: 21/06/2022 12:11:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Diego Capone
-- Create date: 14/02/2022
-- Description:	Inserisce un record nella tabella Nuovi prospect relativa ad una specifica data
-- =============================================
CREATE PROCEDURE [Statistics].[SP_UpdateNuoviProspect]
	@DateGap INT,
	@BusinessName NVARCHAR(50) = '',
	@UtenteUpdate NVARCHAR(50) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	SET datefirst 1;

	DECLARE @DataAttuale DATETIME = GETDATE();
    DECLARE @DateRef DATETIME = DATEADD(day, @DateGap, @DataAttuale);
			
	DECLARE @DateRefWeek INT = DATEPART(WEEK, @DateRef),
			@DateRefYear INT = DATEPART(YEAR, @DateRef),
			@DateRefDay INT = DATEPART(WEEKDAY, @DateRef);

	DECLARE @CountForDay INT = ISNULL((
										SELECT COUNT([R].[id])
										  FROM [dbo].[richiesta] [R]
										  JOIN [Anagraphics].[Cliente_Business] [C_B] ON [R].[cliente_id] = [C_B].[cliente_id]
										  JOIN [Anagraphics].[Business] [B] ON [B].[id] = [C_B].[business_id]
										 WHERE CONVERT(VARCHAR(10),[R].[create_date],103) = CONVERT(VARCHAR(10),@DateRef,103)
										   AND (
													@BusinessName = '' OR
													(
														@BusinessName != '' AND
														[B].[name] = @BusinessName
													)
												)
									),0);

	DECLARE @CountForWeek INT = ISNULL((
										SELECT COUNT([R].[id])
										  FROM [dbo].[richiesta] [R]
										  JOIN [Anagraphics].[Cliente_Business] [C_B] ON [R].[cliente_id] = [C_B].[cliente_id]
										  JOIN [Anagraphics].[Business] [B] ON [B].[id] = [C_B].[business_id]
										 WHERE DATEPART(WEEK, [R].[create_date]) = @DateRefWeek
										   AND DATEPART(YEAR, [R].[create_date]) = @DateRefYear
										   AND (
													@BusinessName = '' OR
													(
														@BusinessName != '' AND
														[B].[name] = @BusinessName
													)
												)
									),0);

	IF EXISTS (
				SELECT [id]
				  FROM [Statistics].[NuoviProspect]
				 WHERE [anno] = @DateRefYear
				   AND [settimana] = @DateRefWeek
				   AND [business] = @BusinessName
			)
			BEGIN
				UPDATE [Statistics].[NuoviProspect]
				   SET [lunedi] = CASE WHEN @DateRefDay = 1 THEN @CountForDay ELSE [lunedi] END
					  ,[martedi] = CASE WHEN @DateRefDay = 2 THEN @CountForDay ELSE [martedi] END
					  ,[mercoledi] = CASE WHEN @DateRefDay = 3 THEN @CountForDay ELSE [mercoledi] END
					  ,[giovedi] = CASE WHEN @DateRefDay = 4 THEN @CountForDay ELSE [giovedi] END
					  ,[venerdi] = CASE WHEN @DateRefDay = 5 THEN @CountForDay ELSE [venerdi] END
					  ,[sabato] = CASE WHEN @DateRefDay = 6 THEN @CountForDay ELSE [sabato] END
					  ,[domenica] = CASE WHEN @DateRefDay = 7 THEN @CountForDay ELSE [domenica] END
					  ,[totale] = @CountForWeek
					  ,[last_mod_user] = @UtenteUpdate
					  ,[last_mod_date] = @DataAttuale
				 WHERE [anno] = @DateRefYear
				   AND [settimana] = @DateRefWeek
				   AND [business] = @BusinessName
			END
		ELSE
			BEGIN
				INSERT INTO [Statistics].[NuoviProspect]
						   ([anno]
						   ,[settimana]
						   ,[business]
						   ,[lunedi]
						   ,[martedi]
						   ,[mercoledi]
						   ,[giovedi]
						   ,[venerdi]
						   ,[sabato]
						   ,[domenica]
						   ,[totale]
						   ,[create_user]
						   ,[create_date])
					 VALUES
						   (@DateRefYear
						   ,@DateRefWeek
						   ,@BusinessName
						   ,CASE WHEN @DateRefDay = 1 THEN @CountForDay ELSE 0 END
						   ,CASE WHEN @DateRefDay = 2 THEN @CountForDay ELSE 0 END
						   ,CASE WHEN @DateRefDay = 3 THEN @CountForDay ELSE 0 END
						   ,CASE WHEN @DateRefDay = 4 THEN @CountForDay ELSE 0 END
						   ,CASE WHEN @DateRefDay = 5 THEN @CountForDay ELSE 0 END
						   ,CASE WHEN @DateRefDay = 6 THEN @CountForDay ELSE 0 END
						   ,CASE WHEN @DateRefDay = 7 THEN @CountForDay ELSE 0 END
						   ,@CountForWeek
						   ,@UtenteUpdate
						   ,@DataAttuale)
			END
END
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "cliente"
            Begin Extent = 
               Top = 7
               Left = 48
               Bottom = 170
               Right = 242
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "richiesta"
            Begin Extent = 
               Top = 7
               Left = 290
               Bottom = 170
               Right = 552
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'vw_uff_garanzie_dash_numeri'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'vw_uff_garanzie_dash_numeri'
GO
