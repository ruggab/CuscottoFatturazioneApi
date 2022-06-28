USE [master]
GO
/****** Object:  Database [CruscottoFatture]    Script Date: 28/06/2022 15:46:49 ******/
CREATE DATABASE [CruscottoFatture]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'CruscottoFatture', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\CruscottoFatture.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'CruscottoFatture_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\CruscottoFatture_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [CruscottoFatture] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [CruscottoFatture].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [CruscottoFatture] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [CruscottoFatture] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [CruscottoFatture] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [CruscottoFatture] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [CruscottoFatture] SET ARITHABORT OFF 
GO
ALTER DATABASE [CruscottoFatture] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [CruscottoFatture] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [CruscottoFatture] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [CruscottoFatture] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [CruscottoFatture] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [CruscottoFatture] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [CruscottoFatture] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [CruscottoFatture] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [CruscottoFatture] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [CruscottoFatture] SET  ENABLE_BROKER 
GO
ALTER DATABASE [CruscottoFatture] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [CruscottoFatture] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [CruscottoFatture] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [CruscottoFatture] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [CruscottoFatture] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [CruscottoFatture] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [CruscottoFatture] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [CruscottoFatture] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [CruscottoFatture] SET  MULTI_USER 
GO
ALTER DATABASE [CruscottoFatture] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [CruscottoFatture] SET DB_CHAINING OFF 
GO
ALTER DATABASE [CruscottoFatture] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [CruscottoFatture] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [CruscottoFatture] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [CruscottoFatture] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [CruscottoFatture] SET QUERY_STORE = OFF
GO
USE [CruscottoFatture]
GO
/****** Object:  User [SQL_Tomcat_Cruscotto]    Script Date: 28/06/2022 15:46:50 ******/
CREATE USER [SQL_Tomcat_Cruscotto] FOR LOGIN [SQL_Tomcat_Cruscotto] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [SQL_Tomcat_Cruscotto]
GO
/****** Object:  Schema [Anagraphics]    Script Date: 28/06/2022 15:46:50 ******/
CREATE SCHEMA [Anagraphics]
GO
/****** Object:  Schema [Logs]    Script Date: 28/06/2022 15:46:50 ******/
CREATE SCHEMA [Logs]
GO
/****** Object:  Table [Anagraphics].[Gruppi]    Script Date: 28/06/2022 15:46:50 ******/
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[ParametriApplicativi]    Script Date: 28/06/2022 15:46:50 ******/
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[Roles]    Script Date: 28/06/2022 15:46:50 ******/
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[roles_voci_menu]    Script Date: 28/06/2022 15:46:50 ******/
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[Users]    Script Date: 28/06/2022 15:46:50 ******/
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [Anagraphics].[voci_menu]    Script Date: 28/06/2022 15:46:50 ******/
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[articolo]    Script Date: 28/06/2022 15:46:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
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
GO
/****** Object:  Table [dbo].[cliente]    Script Date: 28/06/2022 15:46:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cliente](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[codice_cliente] [nvarchar](10) NOT NULL,
	[societa] [nvarchar](4) NULL,
	[ragione_sociale] [nvarchar](35) NOT NULL,
	[codice_fiscale] [nvarchar](16) NULL,
	[partita_iva] [nvarchar](11) NULL,
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
GO
/****** Object:  Table [dbo].[dettaglio_fattura]    Script Date: 28/06/2022 15:46:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dettaglio_fattura](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_fattura] [int] NOT NULL,
	[progressivo_riga] [int] NOT NULL,
	[codice_articolo] [nvarchar](3) NOT NULL,
	[codice_corrispettivo] [nvarchar](3) NOT NULL,
	[importo] [numeric](14, 2) NOT NULL,
	[note] [nvarchar](30) NULL,
	[id_messaggio] [nvarchar](3) NULL,
	[descrizione_messaggio] [nvarchar](50) NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[fattura]    Script Date: 28/06/2022 15:46:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[fattura](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[societa] [nvarchar](4) NOT NULL,
	[data_fattura] [datetime] NOT NULL,
	[tipologia_fattura] [nvarchar](2) NOT NULL,
	[codice_cliente] [nvarchar](10) NOT NULL,
	[importo] [numeric](14, 2) NOT NULL,
	[stato_fattura] [nvarchar](1) NULL,
	[esito_invio] [nvarchar](1) NULL,
	[data_invio_flusso] [datetime] NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[societa]    Script Date: 28/06/2022 15:46:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[societa](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[codice_societa] [nvarchar](4) NOT NULL,
	[descrizione] [nvarchar](35) NOT NULL,
	[create_user] [nvarchar](50) NULL,
	[create_date] [datetime] NULL,
	[last_mod_user] [nvarchar](50) NULL,
	[last_mod_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tipologia_corrispettivi]    Script Date: 28/06/2022 15:46:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
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
GO
/****** Object:  Table [dbo].[utenti_gruppi]    Script Date: 28/06/2022 15:46:50 ******/
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [Logs].[application_logs]    Script Date: 28/06/2022 15:46:50 ******/
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateUpdateRole]    Script Date: 28/06/2022 15:46:50 ******/
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
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateUpdateRoleVociMenu]    Script Date: 28/06/2022 15:46:50 ******/
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
/****** Object:  StoredProcedure [Anagraphics].[SP_CreateUpdateUser]    Script Date: 28/06/2022 15:46:50 ******/
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
/****** Object:  StoredProcedure [Anagraphics].[SP_DeleteRole]    Script Date: 28/06/2022 15:46:50 ******/
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
/****** Object:  StoredProcedure [Anagraphics].[SP_DeleteRoleVociMenu]    Script Date: 28/06/2022 15:46:50 ******/
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
/****** Object:  StoredProcedure [Anagraphics].[SP_DeleteUser]    Script Date: 28/06/2022 15:46:50 ******/
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
/****** Object:  StoredProcedure [Anagraphics].[SP_GetUserByUsername]    Script Date: 28/06/2022 15:46:50 ******/
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
/****** Object:  StoredProcedure [Anagraphics].[SP_LogoutUser]    Script Date: 28/06/2022 15:46:50 ******/
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
/****** Object:  StoredProcedure [Anagraphics].[SP_UpdateUserPassword]    Script Date: 28/06/2022 15:46:50 ******/
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
USE [master]
GO
ALTER DATABASE [CruscottoFatture] SET  READ_WRITE 
GO
