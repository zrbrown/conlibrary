<#macro header title color="#2e8f4d" buttons=[]>
<header class="dialogHeader" style="background-color: ${color};">
    <span class="dialogTitle">${title}</span>
    <div class="dialogHeaderButtons">
        <#list buttons as button><@button/></#list>
    </div>
    <div class="dialogHeaderPadding"></div>
    <button class="svgButton close" formmethod="dialog">
        <svg class="dialogCloseX" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <line fill="none" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" x1="22" x2="2" y1="2" y2="22"></line>
            <line fill="none" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" x1="22" x2="2" y1="22" y2="2"></line>
        </svg>
    </button>
</header>
</#macro>