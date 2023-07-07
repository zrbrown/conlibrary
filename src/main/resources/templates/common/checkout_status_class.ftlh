<#macro checkoutStatusClass checkoutStatus>
<#if checkoutStatus.name() == "NEAR_OVERDUE"> nearOverdue
<#elseif checkoutStatus.name() == "OVERDUE"> overdue
<#elseif checkoutStatus.name() == "EXTREME_OVERDUE"> extremeOverdue
</#if></#macro>