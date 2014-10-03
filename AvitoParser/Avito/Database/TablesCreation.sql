USE [avito]
GO

/*Tables Drops*/
ALTER TABLE [dbo].[Results] DROP CONSTRAINT [FK_Results_Request_Parameters]
GO

DROP TABLE [dbo].[Results]
GO

ALTER TABLE [dbo].[Request_Parameters] DROP CONSTRAINT [FK_Request_Parameters_request]
GO

DROP TABLE [dbo].[Request_Parameters]
GO

DROP TABLE [dbo].[Request]
GO



/*Tables Creation*/
/*"Request" Table Creation*/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Request](
	[request_id] [int] IDENTITY(1,1) NOT NULL,
	[request_date] [datetime] NOT NULL,
 CONSTRAINT [PK_Request_1] PRIMARY KEY CLUSTERED 
(
	[request_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

/*"Request_Parameters" Table Creation*/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Request_Parameters](
	[parameters_id] [int] IDENTITY(1,1) NOT NULL,
	[request_id] [int] NOT NULL,
	[city] [nvarchar](50) NOT NULL,
	[category] [nvarchar](50) NOT NULL,
	[item_name] [nvarchar](200) NOT NULL,
	[owner] [nvarchar](10) NOT NULL,
	[sort] [nvarchar](10) NOT NULL,
	[count] [tinyint] NOT NULL,
	[search_url] [text] NULL,
 CONSTRAINT [PK_Request_Parameters] PRIMARY KEY CLUSTERED 
(
	[parameters_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

ALTER TABLE [dbo].[Request_Parameters]  WITH CHECK ADD  CONSTRAINT [FK_Request_Parameters_request] FOREIGN KEY([request_id])
REFERENCES [dbo].[Request] ([request_id])
GO

ALTER TABLE [dbo].[Request_Parameters] CHECK CONSTRAINT [FK_Request_Parameters_request]
GO


/*"Result" Table Creation*/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Results](
	[result_id] [int] IDENTITY(1,1) NOT NULL,
	[parameters_id] [int] NOT NULL,
	[item_ad_name] [nvarchar](250) NOT NULL,
	[item_description] [text] NULL,
	[seller_name] [nvarchar](70) NOT NULL,
	[seller_phone] [varchar](20) NOT NULL,
	[public_date] [datetime] NOT NULL,
	[price] [nvarchar](10) NULL,
	[item_url] [text] NULL,
 CONSTRAINT [PK_Results] PRIMARY KEY CLUSTERED 
(
	[result_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[Results]  WITH CHECK ADD  CONSTRAINT [FK_Results_Request_Parameters] FOREIGN KEY([parameters_id])
REFERENCES [dbo].[Request_Parameters] ([parameters_id])
GO

ALTER TABLE [dbo].[Results] CHECK CONSTRAINT [FK_Results_Request_Parameters]
GO