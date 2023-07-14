<#import "copy_dialog_buttons.ftlh" as b/>
<div hx-swap-oob="outerHtml:.dialogHeaderButtons">
    <@b.copyEditButton/>
    <@b.copyDeleteButton/>
</div>
<div class="copyInfo" hx-swap-oob="outerHtml:.copyInfo">
    <span>${copy.libraryName()}</span>
    <span>#${copy.libraryCopyId()}</span>
    <span>Owner: ${copy.owner()}</span>
    <p>${copy.notes()}</p>
</div>