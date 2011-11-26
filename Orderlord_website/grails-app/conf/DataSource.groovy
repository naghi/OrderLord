dataSource {
    pooled = true
	driverClassName = "org.hsqldb.jdbcDriver"
	username = "sa"
	password = ""
//	driverClassName = "com.mysql.jdbc.Driver"
//	username = "check-please2"
//	password = "!checkPlease!@"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
//			dbCreate = "update"
			url = "jdbc:hsqldb:mem:devDB"
//			url = "jdbc:mysql://98.248.144.183:3306/new_schema?autoreconnect=true"
//			url = "jdbc:mysql://107.20.135.212:3306/check-please2?autoreconnect=true"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
//          url = "jdbc:hsqldb:mem:testDb"
			url = "jdbc:mysql://107.20.135.212:3306/check-please2?autoreconnect=true"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
//          url = "jdbc:hsqldb:file:prodDb;shutdown=true"
//			url = "jdbc:mysql://98.248.144.183:3306/new_schema?autoreconnect=true"
			url = "jdbc:mysql://107.20.135.212:3306/check-please2?autoreconnect=true"
        }
    }
}
