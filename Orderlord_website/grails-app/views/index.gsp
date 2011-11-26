<html>
    <head>
        <title>Welcome to Orderlord</title>
        <meta name="layout" content="main" />
        <style type="text/css" media="screen">

        #nav {
            margin-top:20px;
            margin-left:30px;
            width:228px;
            float:left;

        }
        .homePagePanel * {
            margin:0px;
        }
        .homePagePanel .panelBody ul {
            list-style-type:none;
            margin-bottom:10px;
        }
        .homePagePanel .panelBody h1 {
            text-transform:uppercase;
            font-size:1.1em;
            margin-bottom:10px;
        }
        .homePagePanel .panelBody {
            background: url(images/leftnav_midstretch.png) repeat-y top;
            margin:0px;
            padding:15px;
        }
        .homePagePanel .panelBtm {
            background: url(images/leftnav_btm.png) no-repeat top;
            height:20px;
            margin:0px;
        }

        .homePagePanel .panelTop {
            background: url(images/leftnav_top.png) no-repeat top;
            height:11px;
            margin:0px;
        }
        h2 {
            margin-top:15px;
            margin-bottom:15px;
            font-size:1.2em;
        }
        #pageBody {
            margin-left:280px;
            margin-right:20px;
        }
        </style>
    </head>
    <body>
        <div id="nav">
            <div class="homePagePanel">
                <div class="panelTop"></div>
                <div class="panelBody">
                    <h1>Application Status</h1>
                    <ul>
                        <li>App version: <g:meta name="app.version"></g:meta></li>
                        <li>Grails version: <g:meta name="app.grails.version"></g:meta></li>
                        <li>Groovy version: ${org.codehaus.groovy.runtime.InvokerHelper.getVersion()}</li>
                        <li>JVM version: ${System.getProperty('java.version')}</li>
                        <li>Controllers: ${grailsApplication.controllerClasses.size()}</li>
                        <li>Domains: ${grailsApplication.domainClasses.size()}</li>
                        <li>Services: ${grailsApplication.serviceClasses.size()}</li>
                        <li>Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
                    </ul>
                    <h1>Installed Plugins</h1>
                    <ul>
                        <g:set var="pluginManager"
                               value="${applicationContext.getBean('pluginManager')}"></g:set>

                        <g:each var="plugin" in="${pluginManager.allPlugins}">
                            <li>${plugin.name} - ${plugin.version}</li>
                        </g:each>

                    </ul>
                </div>
                <div class="panelBtm"></div>
            </div>
        </div>
        <div id="pageBody">
            <h1>Welcome to Orderlord, ${storename}.</h1>
            
            <div id="controllerList" class="dialog">
                <h2 style="font-size:14px;">Available Controllers:</h2>
                <ul>
                    <li class="controller" style="margin-top:2px;"><g:link controller="activeorder" style="font-size:12px;">Display active orders</g:link></li>
                    <li class="controller" style="margin-top:14px;"><g:link controller="item" style="font-size:12px;">Edit store's menu</g:link></li>
                    <li class="controller" style="margin-top:2px;"><g:link controller="store" style="font-size:12px;">Edit store's info</g:link></li>
                  
                   <g:if test="${session?.store?.admin}">
                    <li class="controller" style="margin-top:14px;"><g:link controller="pendingorder" style="font-size:12px;">Display pending orders (admins only)</g:link></li>
                    <li class="controller" style="margin-top:2px;"><g:link controller="customer" style="font-size:12px;">Customers (admins only)</g:link></li>
                   </g:if>
                </ul>
            </div>
        </div>
    </body>
</html>
