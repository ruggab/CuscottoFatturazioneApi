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



ALTER PROCEDURE [Anagraphics].[SP_CreateUpdateUser]
	@id INT = -1,
	@role_id INT,
	@name NVARCHAR(50) = '',
	@email NVARCHAR(250) = '',
	@societa NVARCHAR(300) = '',
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
				  ,[societa] = @societa
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
					   ,[societa]
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
					   ,@societa
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
