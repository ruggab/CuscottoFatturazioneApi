USE [CruscottoFatture]
GO
/****** Object:  View [dbo].[VWdashboardFatture]    Script Date: 29/06/2022 17:58:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[VWdashboardFatture]
AS
SELECT        CASE WHEN stato_fattura IS NULL THEN 'I' ELSE stato_fattura END AS stato_fattura, COUNT(id) AS numero, SUM(importo) AS importo
FROM            dbo.fattura
GROUP BY stato_fattura
GO
/****** Object:  View [dbo].[VWdashboardFattureLastWeek]    Script Date: 29/06/2022 17:58:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[VWdashboardFattureLastWeek]
AS
SELECT        TOP (100) PERCENT DATEPART(DAY, data_fattura) AS giorno, DATENAME(WEEKDAY, data_fattura) AS giorno_settimana, COUNT(id) AS numero
FROM            dbo.fattura
WHERE        (data_fattura > DATEADD(day, - 7, GETDATE())) AND (data_fattura <= GETDATE())
GROUP BY DATEPART(DAY, data_fattura), DATEPART(MONTH, data_fattura), DATENAME(WEEKDAY, data_fattura)
ORDER BY DATEPART(MONTH, data_fattura), giorno
GO
/****** Object:  View [dbo].[VWdashboardFattureMonth]    Script Date: 29/06/2022 17:58:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[VWdashboardFattureMonth]
AS
SELECT        TOP (100) PERCENT DATEPART(WEEK, data_fattura) AS settimana, COUNT(id) AS numero
FROM            dbo.fattura
WHERE        (DATEPART(MONTH, data_fattura) = DATEPART(MONTH, GETDATE()))
GROUP BY DATEPART(WEEK, data_fattura)
ORDER BY settimana
GO
/****** Object:  View [dbo].[VWdashboardFattureYear]    Script Date: 29/06/2022 17:58:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[VWdashboardFattureYear]
AS
SELECT        TOP (100) PERCENT DATEPART(MONTH, data_fattura) AS mese, DATENAME(MONTH, data_fattura) AS nome_mese, COUNT(id) AS numero
FROM            dbo.fattura
WHERE        (YEAR(data_fattura) = YEAR(GETDATE()))
GROUP BY DATEPART(MONTH, data_fattura), DATENAME(MONTH, data_fattura)
ORDER BY mese
GO

/****** Object:  StoredProcedure [dbo].[getFattureLastWeek]    Script Date: 29/06/2022 17:58:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getFattureLastWeek]   
    @societa nvarchar(4)
	AS   
	
	SET LANGUAGE Italian;
	SET DATEFIRST 1;
	SELECT         DATEPART(DAY, data_fattura) AS giorno,  DATENAME(WEEKDAY,data_fattura) as giorno_settimana, COUNT(id) AS numero
	FROM            dbo.fattura
	WHERE data_fattura > DATEADD(day,-7, GETDATE()) and  data_fattura <=  GETDATE()
	AND societa=@societa
	GROUP BY DATEPART(DAY, data_fattura), DATEPART(MONTH, data_fattura), DATENAME(WEEKDAY,data_fattura)
	ORDER BY DATEPART(MONTH, data_fattura), giorno;
GO
/****** Object:  StoredProcedure [dbo].[getFattureMonth]    Script Date: 29/06/2022 17:58:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[getFattureMonth]   
    @societa nvarchar(4),
	@stato nvarchar(1)
	AS   
	
	SET LANGUAGE Italian;
	SET DATEFIRST 1;
	SELECT        DATEPART(WEEK, data_fattura) AS settimana, COUNT(id) AS numero
	FROM            dbo.fattura
	WHERE         DATEPART(MONTH, data_fattura) = DATEPART(MONTH, GETDATE())

	AND societa=@societa
	AND stato_fattura=@stato
	GROUP BY DATEPART(WEEK, data_fattura)
	ORDER BY settimana
GO
/****** Object:  StoredProcedure [dbo].[getFattureYear]    Script Date: 29/06/2022 17:58:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getFattureYear]   
    @societa nvarchar(4)
	AS   
	
	SET LANGUAGE Italian;
	SET DATEFIRST 1;
	SELECT        DATEPART(MONTH, data_fattura) AS mese, DATENAME(MONTH, data_fattura) AS nome_mese, COUNT(id) AS numero
	FROM            dbo.fattura
	WHERE        (YEAR(data_fattura) = YEAR(GETDATE()))

	AND societa=@societa
	GROUP BY DATEPART(MONTH, data_fattura), DATENAME(MONTH, data_fattura)
	ORDER BY mese
GO
/****** Object:  StoredProcedure [dbo].[getTopSummary]    Script Date: 29/06/2022 17:58:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getTopSummary]   
    @societa nvarchar(50)
AS   

  SELECT        CASE WHEN stato_fattura  IS NULL THEN 'I' ELSE stato_fattura END as stato_fattura, COUNT(id) AS numero, SUM(importo) AS importo
	FROM            dbo.fattura
	WHERE  societa=@societa
	GROUP BY stato_fattura
	
GO