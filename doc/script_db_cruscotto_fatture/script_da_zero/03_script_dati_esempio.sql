USE [CruscottoFatture]
GO
SET IDENTITY_INSERT [dbo].[articolo] ON 

INSERT [dbo].[articolo] ([id], [codice_articolo], [descrizione], [data_validita], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (1, N'221', N'articolo 221', CAST(N'2022-12-27T01:00:00.000' AS DateTime), N'admin', CAST(N'2022-06-28T15:50:55.637' AS DateTime), NULL, NULL)
INSERT [dbo].[articolo] ([id], [codice_articolo], [descrizione], [data_validita], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (2, N'222', N'articolo 222', CAST(N'2022-12-27T01:00:00.000' AS DateTime), N'admin', CAST(N'2022-06-28T15:51:00.477' AS DateTime), NULL, NULL)
INSERT [dbo].[articolo] ([id], [codice_articolo], [descrizione], [data_validita], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (3, N'223', N'articolo 223', CAST(N'2022-12-27T01:00:00.000' AS DateTime), N'admin', CAST(N'2022-06-28T15:51:04.527' AS DateTime), NULL, NULL)
INSERT [dbo].[articolo] ([id], [codice_articolo], [descrizione], [data_validita], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (4, N'224', N'articolo 224', CAST(N'2022-12-27T01:00:00.000' AS DateTime), N'admin', CAST(N'2022-06-28T15:51:08.463' AS DateTime), NULL, NULL)
INSERT [dbo].[articolo] ([id], [codice_articolo], [descrizione], [data_validita], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (5, N'225', N'articolo 225', CAST(N'2022-06-28T00:00:00.000' AS DateTime), N'admin', CAST(N'2022-06-28T15:51:15.057' AS DateTime), N'admin', CAST(N'2022-06-28T15:51:36.033' AS DateTime))
SET IDENTITY_INSERT [dbo].[articolo] OFF
GO
SET IDENTITY_INSERT [dbo].[cliente] ON 

INSERT [dbo].[cliente] ([id], [codice_cliente], [societa], [ragione_sociale], [codice_fiscale], [partita_iva], [nazionalita], [sede_legale], [appartiene_gruppo_iva], [codice_destinatario_fatturazione], [modalita_pagamento], [condizioni_pagamento], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (1, N'1234567890', N'soc1', N'rag soc1', N'ffyynf65d33j876p', N'12345678951', N'IT', N'Milano', N'X', N'225874', N'30 gg', N'ass', N'admin', CAST(N'2022-06-28T15:49:54.480' AS DateTime), NULL, NULL)
INSERT [dbo].[cliente] ([id], [codice_cliente], [societa], [ragione_sociale], [codice_fiscale], [partita_iva], [nazionalita], [sede_legale], [appartiene_gruppo_iva], [codice_destinatario_fatturazione], [modalita_pagamento], [condizioni_pagamento], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (3, N'1234567890', N'soc2', N'rag soc2', N'ffyynf65d33j876p', N'12345678951', N'IT', N'Milano', N'X', N'225874', N'30 gg', N'ass', N'admin', CAST(N'2022-06-28T15:50:39.507' AS DateTime), NULL, NULL)
SET IDENTITY_INSERT [dbo].[cliente] OFF
GO
SET IDENTITY_INSERT [dbo].[dettaglio_fattura] ON 

INSERT [dbo].[dettaglio_fattura] ([id], [id_fattura], [progressivo_riga], [codice_articolo], [codice_corrispettivo], [importo], [note], [id_messaggio], [descrizione_messaggio], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (1, 1, 1, N'125', N'1', CAST(300.34 AS Numeric(14, 2)), N'test', NULL, NULL, N'admin', CAST(N'2022-06-28T15:53:26.767' AS DateTime), NULL, NULL)
INSERT [dbo].[dettaglio_fattura] ([id], [id_fattura], [progressivo_riga], [codice_articolo], [codice_corrispettivo], [importo], [note], [id_messaggio], [descrizione_messaggio], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (2, 1, 2, N'123', N'1', CAST(340.64 AS Numeric(14, 2)), N'test', NULL, NULL, N'admin', CAST(N'2022-06-28T15:53:36.737' AS DateTime), NULL, NULL)
SET IDENTITY_INSERT [dbo].[dettaglio_fattura] OFF
GO
SET IDENTITY_INSERT [dbo].[fattura] ON 

INSERT [dbo].[fattura] ([id], [societa], [data_fattura], [tipologia_fattura], [codice_cliente], [importo], [stato_fattura], [esito_invio], [data_invio_flusso], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (1, N'soc4', CAST(N'2022-06-06T02:00:00.000' AS DateTime), N'FA', N'1234567892', CAST(1500.11 AS Numeric(14, 2)), N'R', NULL, NULL, N'admin', CAST(N'2022-06-28T15:52:15.947' AS DateTime), NULL, NULL)
INSERT [dbo].[fattura] ([id], [societa], [data_fattura], [tipologia_fattura], [codice_cliente], [importo], [stato_fattura], [esito_invio], [data_invio_flusso], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (2, N'soc4', CAST(N'2022-06-29T02:00:00.000' AS DateTime), N'FA', N'1234567892', CAST(1500.34 AS Numeric(14, 2)), N'G', NULL, NULL, N'admin', CAST(N'2022-06-28T15:52:23.817' AS DateTime), NULL, NULL)
INSERT [dbo].[fattura] ([id], [societa], [data_fattura], [tipologia_fattura], [codice_cliente], [importo], [stato_fattura], [esito_invio], [data_invio_flusso], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (6, N'soc4', CAST(N'2022-06-01T02:00:00.000' AS DateTime), N'FA', N'123589', CAST(1500.00 AS Numeric(14, 2)), N'R', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[fattura] ([id], [societa], [data_fattura], [tipologia_fattura], [codice_cliente], [importo], [stato_fattura], [esito_invio], [data_invio_flusso], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (8, N'soc4', CAST(N'2022-06-23T02:00:00.000' AS DateTime), N'FA', N'23443', CAST(2500.00 AS Numeric(14, 2)), NULL, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[fattura] ([id], [societa], [data_fattura], [tipologia_fattura], [codice_cliente], [importo], [stato_fattura], [esito_invio], [data_invio_flusso], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (11, N'soc4', CAST(N'2022-06-24T02:00:00.000' AS DateTime), N'FA', N'11133', CAST(59824.00 AS Numeric(14, 2)), N'R', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[fattura] ([id], [societa], [data_fattura], [tipologia_fattura], [codice_cliente], [importo], [stato_fattura], [esito_invio], [data_invio_flusso], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (13, N'soc4', CAST(N'2022-06-06T02:00:00.000' AS DateTime), N'FA', N'23343', CAST(785.00 AS Numeric(14, 2)), N'R', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[fattura] ([id], [societa], [data_fattura], [tipologia_fattura], [codice_cliente], [importo], [stato_fattura], [esito_invio], [data_invio_flusso], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (14, N'soc4', CAST(N'2022-06-13T02:00:00.000' AS DateTime), N'FA', N'23343', CAST(258.00 AS Numeric(14, 2)), N'R', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[fattura] ([id], [societa], [data_fattura], [tipologia_fattura], [codice_cliente], [importo], [stato_fattura], [esito_invio], [data_invio_flusso], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (15, N'soc4', CAST(N'2022-06-29T12:00:00.000' AS DateTime), N'FA', N'23343', CAST(785.00 AS Numeric(14, 2)), N'R', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[fattura] ([id], [societa], [data_fattura], [tipologia_fattura], [codice_cliente], [importo], [stato_fattura], [esito_invio], [data_invio_flusso], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (16, N'soc4', CAST(N'2022-06-29T02:00:00.000' AS DateTime), N'FA', N'23343', CAST(785.00 AS Numeric(14, 2)), N'R', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[fattura] ([id], [societa], [data_fattura], [tipologia_fattura], [codice_cliente], [importo], [stato_fattura], [esito_invio], [data_invio_flusso], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (17, N'soc4', CAST(N'2022-06-29T12:00:00.000' AS DateTime), N'FA', N'23343', CAST(785.00 AS Numeric(14, 2)), NULL, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[fattura] ([id], [societa], [data_fattura], [tipologia_fattura], [codice_cliente], [importo], [stato_fattura], [esito_invio], [data_invio_flusso], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (18, N'soc4', CAST(N'2022-06-20T02:00:00.000' AS DateTime), N'FA', N'23343', CAST(785.00 AS Numeric(14, 2)), N'R', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[fattura] ([id], [societa], [data_fattura], [tipologia_fattura], [codice_cliente], [importo], [stato_fattura], [esito_invio], [data_invio_flusso], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (19, N'soc4', CAST(N'2022-06-27T02:00:00.000' AS DateTime), N'FA', N'23343', CAST(785.00 AS Numeric(14, 2)), N'R', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[fattura] ([id], [societa], [data_fattura], [tipologia_fattura], [codice_cliente], [importo], [stato_fattura], [esito_invio], [data_invio_flusso], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (20, N'soc4', CAST(N'2022-08-03T02:00:00.000' AS DateTime), N'FA', N'23343', CAST(785.00 AS Numeric(14, 2)), N'R', NULL, NULL, NULL, NULL, NULL, NULL)
SET IDENTITY_INSERT [dbo].[fattura] OFF
GO
SET IDENTITY_INSERT [dbo].[societa] ON 

INSERT [dbo].[societa] ([id], [codice_societa], [descrizione], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (1, N'soc1', N'soc1 srl', N'admin', CAST(N'2022-06-28T15:39:46.863' AS DateTime), NULL, NULL)
INSERT [dbo].[societa] ([id], [codice_societa], [descrizione], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (2, N'soc2', N'soc2 srl', N'admin', CAST(N'2022-06-28T15:47:26.307' AS DateTime), NULL, NULL)
INSERT [dbo].[societa] ([id], [codice_societa], [descrizione], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (3, N'soc3', N'soc3 srl', N'admin', CAST(N'2022-06-28T15:47:30.953' AS DateTime), NULL, NULL)
SET IDENTITY_INSERT [dbo].[societa] OFF
GO
SET IDENTITY_INSERT [dbo].[tipologia_corrispettivi] ON 

INSERT [dbo].[tipologia_corrispettivi] ([id], [codice_corrispettivo], [descrizione], [data_validita], [create_user], [create_date], [last_mod_user], [last_mod_date]) VALUES (1, N'227', N'corrispettivo 227', CAST(N'2022-06-28T00:00:00.000' AS DateTime), N'admin', CAST(N'2022-06-28T15:51:53.643' AS DateTime), N'admin', CAST(N'2022-06-28T15:52:02.333' AS DateTime))
SET IDENTITY_INSERT [dbo].[tipologia_corrispettivi] OFF
GO
