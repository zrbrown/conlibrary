<#import "../common/dialog_header.ftlh" as h/>
<@h.header "Confirm delete"/>
<div class="alertDialogContent">
    <div class="primary">
        Are you sure you want to delete?
        <br/>
        <span>${copy.owner()}</span>'s <span>${game.title()} #${copy.libraryId()}</span>
    </div>
</div>
