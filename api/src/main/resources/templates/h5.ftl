<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

        <!-- Title of the page -->
        <title>${title}</title>

        <!-- Mobi.css -->
        <link rel="stylesheet" href="${contextPath}/mobi/mobi.min.css">
        <style type="text/css">
            /** p自动缩进 */
            #container p{
                text-indent: 2em;
            }
        </style>
    </head>
    <body>
        <div class="flex-center">
            <div class="container">
                <#if title??>
                    <h1 class="text-center top-gap" >${title}</h1>
                </#if>
                <div>
                    ${content}
                </div>
            </div>
        </div>
    </body>
</html>