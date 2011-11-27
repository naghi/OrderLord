
    <h1><g:message code="Pendingorder List (ordered by ascending pickupTime)" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
    </g:if>
    <div class="list" id="refreshTable">
        <table>
            <thead>
                <tr>
                
                    <th><g:message code="pendingorder.id.label" default="Id" /></th>
                    
                    <th><g:message code="pendingorder.orderType.label" default="Order Type" /></th>
                    
                    <th><g:message code="pendingorder.scheduleDay.label" default="Schedule Day" /></th>
                
                    <th><g:message code="pendingorder.pickupTime.label" default="Pickup Time" /></th>
                
                    <th><g:message code="pendingorder.orderEtp.label" default="Order ETP" /></th>
                
                    <th><g:message code="pendingorder.totalCost.label" default="Total Cost" /></th>
                
                    <th><g:message code="pendingorder.customer.label" default="Customer" /></th>
                    
                    <th><g:message code="pendingorder.store.label" default="Store" /></th>
                
                </tr>
            </thead>
            <tbody>
            <g:each in="${pendingorderInstanceList}" status="i" var="pendingorderInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                
                    <td><g:link action="show" id="${pendingorderInstance.id}">${fieldValue(bean: pendingorderInstance, field: "id")}</g:link></td>
                    
                    <td>${pendingorderInstance.orderType}</td>
                    
                    <td>${pendingorderInstance.toDisplayDay()}</td>
                
                    <td>${pendingorderInstance.toDisplayTime()}</td>
                
                    <td>${pendingorderInstance.orderEtp} min</td>
                
                    <td>$${pendingorderInstance.totalCost}</td>
                
                    <td>${fieldValue(bean: pendingorderInstance, field: "customer")}</td>
                    
                    <td>${fieldValue(bean: pendingorderInstance, field: "store")}</td>
                
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="paginateButtons">
        <g:paginate total="${pendingorderInstanceTotal}" />
    </div>
