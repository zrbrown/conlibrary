<#import "copy_dialog_buttons.ftlh" as b/>
<span hx-swap-oob="outerHtml:.dialogTitle">${game.title()}</span>
<div hx-swap-oob="outerHtml:.dialogHeaderButtons">
    <@b.copyEditButton/>
    <@b.copyDeleteButton/>
</div>
<div class="copyInfo" hx-swap-oob="outerHtml:.copyInfo">
    <span>${copy.library()} #${copy.libraryId()}</span>
    <span>Owner: ${copy.owner()}</span>
    <p>${copy.notes()}</p>
</div>