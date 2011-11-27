
    <h1><g:message code="Activeorder List (ordered by ascending pickupTime)" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
    </g:if>
    <div class="list" id="refreshTable">
        <table>
            <thead>
                <tr>
                
                   <g:if test="${session?.store?.admin}">
                    <!-- g:sortableColumn property="id" title="${message(code: 'activeorder.id.label', default: 'Id')}" /-->
                    <th><g:message code="activeorder.id.label" default="Id" /></th>
                   </g:if>
                    <th><g:message code="activeorder.pickupTime.label" default="Pickup Time" /></th>
                
                    <th><g:message code="activeorder.orderEtp.label" default="Order ETP" /></th>
                
                    <th><g:message code="activeorder.totalCost.label" default="Total Cost" /></th>
                
                    <th><g:message code="activeorder.customer.label" default="Customer" /></th>
                
                    <th><g:message code="activeorder.store.label" default="Store" /></th>
                
                </tr>
            </thead>
            <tbody>
            <g:each in="${activeorderInstanceList}" status="i" var="activeorderInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                
                   <g:if test="${session?.store?.admin}">
                    <td><g:link action="show" id="${activeorderInstance.id}">${fieldValue(bean: activeorderInstance, field: "id")}</g:link></td>
                   </g:if>
                
                    <!-- td><g:formatDate date="${activeorderInstance.pickupTime}" /></td-->
                    
                    <td><g:link action="show" id="${activeorderInstance.id}">${activeorderInstance.toDisplayDateTime()}</g:link></td>
                
                    <td>${activeorderInstance.orderEtp} min</td>
                
                    <td>$${activeorderInstance.totalCost}</td>
                
                    <td>${fieldValue(bean: activeorderInstance, field: "customer")}</td>
                
                    <td>${fieldValue(bean: activeorderInstance, field: "store")}</td>
                
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="paginateButtons">
        <g:paginate total="${activeorderInstanceTotal}" />
    </div>

