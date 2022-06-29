CREATE OR ALTER VIEW [dbo].[VWdashboardFattureLastWeek]
AS
SELECT        TOP (100) PERCENT FORMAT(CAST(data_fattura AS date), 'yyyy-MM-dd') AS giorno, COUNT(id) AS numero
FROM            dbo.fattura
WHERE        (data_fattura > DATEADD(day, - 7, GETDATE())) AND (data_fattura <= GETDATE())
GROUP BY CAST(data_fattura AS date)
ORDER BY CAST(data_fattura AS date);


CREATE OR ALTER PROCEDURE [dbo].[getFattureLastWeek]   
    @societa nvarchar(4)
	AS   
	
	SET LANGUAGE Italian;
	SET DATEFIRST 1;
	SELECT       FORMAT(cast(data_fattura as date), 'yyyy-MM-dd') as giorno, COUNT(id) AS numero
FROM            dbo.fattura
WHERE        (data_fattura > DATEADD(day, - 7, GETDATE())) AND (data_fattura <= GETDATE())

	AND societa=@societa
GROUP BY cast(data_fattura as date)
ORDER BY cast(data_fattura as date);
GO