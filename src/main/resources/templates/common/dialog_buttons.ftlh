<#macro editButton url>
    <button class="svgButton edit" hx-get=${url} hx-swap="none">
        <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
            <g id="SVGRepo_iconCarrier">
                <path fill-rule="evenodd" clip-rule="evenodd" d="M14.2322 5.76777C15.2085 4.79146 16.7915 4.79146 17.7678 5.76777L18.4749 6.47487C19.4512 7.45118 19.4512 9.0341 18.4749 10.0104L10.3431 18.1421L7.10051 18.1421C6.54822 18.1421 6.1005 17.6944 6.10051 17.1421L6.10051 13.8995L14.2322 5.76777ZM16.3536 7.18198L17.0607 7.88909C17.2559 8.08435 17.2559 8.40093 17.0607 8.59619L16 9.65685L14.5858 8.24264L15.6464 7.18198C15.8417 6.98672 16.1583 6.98672 16.3536 7.18198ZM14.5858 11.0711L9.51472 16.1421L8.10051 16.1421L8.10051 14.7279L13.1716 9.65685L14.5858 11.0711Z"></path>
            </g>
        </svg>
    </button>
</#macro>
<#macro deleteButton url>
    <button class="svgButton delete" hx-delete=${url} hx-target="#mainDialogContent">
        <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier">
                <path d="M10 11V17" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                <path d="M14 11V17" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                <path d="M4 7H20" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                <path d="M6 7H12H18V18C18 19.6569 16.6569 21 15 21H9C7.34315 21 6 19.6569 6 18V7Z" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                <path d="M9 5C9 3.89543 9.89543 3 11 3H13C14.1046 3 15 3.89543 15 5V7H9V5Z" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
            </g>
        </svg>
    </button>
</#macro>
<#macro cancelButton url>
    <button class="svgButton cancel" hx-get="${url}" hx-swap="none">
        <svg viewBox="0 0 21 21" xmlns="http://www.w3.org/2000/svg" fill="#000000">
            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
            <g id="SVGRepo_iconCarrier">
                <g fill="none" fill-rule="evenodd" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" transform="translate(2 2)">
                    <path d="m12.5 1.5c2.4138473 1.37729434 4 4.02194088 4 7 0 4.418278-3.581722 8-8 8s-8-3.581722-8-8 3.581722-8 8-8"></path>
                    <path d="m12.5 5.5v-4h4"></path>
                </g>
            </g>
        </svg>
    </button>
</#macro>
<#macro saveButton url>
    <button class="svgButton save" hx-patch=${url} hx-swap="none">
        <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier">
                <path d="M8 20H6C4.89543 20 4 19.1046 4 18V6C4 4.89543 4.89543 4 6 4H9M8 20V14C8 13.4477 8.44772 13 9 13H15C15.5523 13 16 13.4477 16 14V20M8 20H16M16 20H18C19.1046 20 20 19.1046 20 18V8.82843C20 8.29799 19.7893 7.78929 19.4142 7.41421L16.5858 4.58579C16.2107 4.21071 15.702 4 15.1716 4H15M15 4V7C15 7.55228 14.5523 8 14 8H10C9.44772 8 9 7.55228 9 7V4M15 4H9" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
            </g>
        </svg>
    </button>
</#macro>