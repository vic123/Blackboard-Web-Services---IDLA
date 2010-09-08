
EXEC BackupDBList @DBList = 'bb_bb60,bb_jreport,bb_bb60_stats,bbadmin,cms,cms_files_courses,cms_files_inst,cms_files_library,cms_files_orgs,cms_files_users', 
	@BackupDir = 'I:\Project\Bb\src\WS\wservices_idla\data\', @LogToSQLAdmin = 0 

--note that bb_bb60 cms names in FilePrefixList has to be specified more precisely (in sample below they include first digit of date) because otherwise bb_bb60_stats or cms_files_* backup files are matched 
EXEC RestoreDBList @DBList = 'bb_bb60,bb_jreport,bb_bb60_stats,bbadmin,cms,cms_files_courses,cms_files_inst,cms_files_library,cms_files_orgs,cms_files_users', 
				@FilePrefixList = 'bb_bb60_2,bb_jreport,bb_bb60_stats,bbadmin,cms_2,cms_files_courses,cms_files_inst,cms_files_library,cms_files_orgs,cms_files_users',  
				@BackupDir = 'I:\Project\Bb\src\WS\wservices_idla\data\',
				@DataDir = 'I:\blackboard\mssql\data',
				@DoRecover = 1, 
				@ListDelimiter = ',', 
				@LogToSQLAdmin = 0 


drop database bb_bb60
drop database bb_jreport
drop database bb_bb60_stats
drop database bbadmin
drop database cms
drop database cms_files_courses
drop database cms_files_inst
drop database cms_files_library
drop database cms_files_orgs
drop database cms_files_users
