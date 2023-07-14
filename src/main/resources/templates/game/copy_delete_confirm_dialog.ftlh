<#import "../common/dialog_header.ftlh" as h/>
<@h.header "Confirm delete" "#ab0000"/>
<div class="alertDialogContent">
    <div class="primary">
        Are you sure you want to delete?
        <br/>
        <span>${copy.owner()}</span>'s <span>${copy.gameTitle()} #${copy.libraryCopyId()}</span>
    </div>
</div>
