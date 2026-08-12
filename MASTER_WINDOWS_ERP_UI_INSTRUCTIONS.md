# MASTER INSTRUCTIONS --- نظام المحاسبة وإدارة المخزون لمحل أدوات الشبكات والإلكترونيات

## 1. تعريف المشروع

هذا المستند هو المرجع الرئيسي لتطوير نظام محاسبي وإداري احترافي لمحل
أدوات الشبكات والإلكترونيات.

الهدف النهائي هو إنتاج نظام متكامل يعمل على:

-   Android
-   Windows Desktop

مع دعم:

-   Online
-   Offline
-   Synchronization بين الأجهزة
-   المبيعات
-   المشتريات
-   المخزون والمخازن
-   العملاء
-   الموردين
-   الحسابات
-   المصروفات
-   السندات
-   الفواتير
-   المرتجعات
-   الخصومات
-   التقارير
-   الطباعة
-   WhatsApp
-   الإعدادات
-   المستخدمين والصلاحيات
-   المحاسبة عند وجود منطقها في المشروع

------------------------------------------------------------------------

# 2. الهدف التجاري

النظام مخصص لإدارة محل أدوات الشبكات والإلكترونيات، ويجب أن يكون قادرًا
على إدارة دورة العمل اليومية كاملة.

من أمثلة المنتجات:

-   MikroTik
-   TP-Link
-   Ubiquiti
-   D-Link
-   Switches
-   Routers
-   Access Points
-   كابلات الشبكات
-   Cat5/Cat6
-   RJ45
-   Power Adapters
-   أجهزة الشبكات
-   الإكسسوارات الإلكترونية
-   أي منتجات أخرى يدعمها النظام

ويجب استخدام بيانات عربية واقعية في واجهات المعاينة بدل:

-   Product 1
-   Customer 1
-   Test
-   Lorem ipsum

------------------------------------------------------------------------

# 3. المنصات المطلوبة

## Android

تطبيق Android احترافي، RTL، مخصص للموبايل، وليس مجرد نسخة مكبرة من
Windows.

## Windows

تطبيق Desktop حقيقي يستفيد من:

-   الشاشة الكبيرة
-   الماوس
-   لوحة المفاتيح
-   DataGrid
-   الجداول
-   النوافذ
-   أدوات البحث
-   التصفية
-   القوائم السياقية
-   الاختصارات
-   الطباعة
-   معاينة الفواتير والتقارير

### قاعدة أساسية

لا يتم تحويل واجهة Android إلى Windows حرفيًا.

يجب استخدام نفس الهوية والوظائف فقط، ثم بناء تجربة Desktop حقيقية.

------------------------------------------------------------------------

# 4. المزامنة Online / Offline

النظام يجب أن يعمل:

## Online

مع البيانات المتزامنة مع الخادم.

## Offline

يستطيع المستخدم العمل عند انقطاع الإنترنت، مع حفظ البيانات محليًا.

## عند عودة الاتصال

يتم:

1.  اكتشاف الاتصال.
2.  رفع التغييرات المحلية.
3.  استقبال التغييرات الجديدة.
4.  مزامنة الأجهزة.
5.  تحديث حالة المزامنة.

### واجهة المزامنة

يجب أن تعرض حالة واضحة مثل:

-   Online
-   Offline
-   Syncing
-   Sync Failed
-   Last Sync
-   Pending Changes

وإذا كان منطق المزامنة موجودًا بالفعل، لا يتم إعادة اختراعه داخل الواجهة.

------------------------------------------------------------------------

# 5. WhatsApp

النظام مطلوب أن يدعم إرسال التقارير والمستندات عبر WhatsApp للعملاء
والموردين.

عند وجود الوظيفة في المنطق، يجب توفير UI لـ:

-   WhatsApp Center
-   Message Templates
-   Invoice Share
-   Customer Statement Share
-   Supplier Statement Share
-   Report Share
-   Message Preview
-   Send Status

### قاعدة

الواجهة فقط.

لا يتم اختراع WhatsApp API أو Business Logic جديد إذا لم يكن موجودًا.

------------------------------------------------------------------------

# 6. الطباعة

النظام يجب أن يدعم طباعة:

-   الفواتير
-   السندات
-   التقارير
-   كشوف الحساب
-   المستندات
-   الإيصالات

ويجب توفير Print Preview احترافي.

واجهة الطباعة عند الحاجة تحتوي على:

-   Printer
-   Paper Size
-   Copies
-   Margins
-   Preview
-   Print
-   Export PDF

وعند وجودها في النظام يمكن دعم:

-   A4
-   A5
-   Receipt
-   Custom

------------------------------------------------------------------------

# 7. الوظائف الأساسية

## المبيعات

يجب أن تغطي الواجهات الموجودة في المنطق:

-   المبيعات
-   نقطة البيع POS
-   الفواتير
-   تفاصيل الفاتورة
-   الفواتير الآجلة
-   المدفوعات
-   الخصومات
-   المرتجعات
-   الفواتير المعلقة
-   إلغاء الفاتورة
-   معاينة الفاتورة
-   طباعة الفاتورة
-   مشاركة الفاتورة

------------------------------------------------------------------------

## المشتريات

-   المشتريات
-   فواتير الشراء
-   تفاصيل الشراء
-   مرتجعات المشتريات
-   مدفوعات الموردين
-   معاينة وطباعة مستندات الشراء

------------------------------------------------------------------------

## المنتجات

-   قائمة المنتجات
-   إضافة منتج
-   تعديل منتج
-   تفاصيل المنتج
-   SKU
-   Barcode
-   Category
-   Brand
-   Cost
-   Retail Price
-   Wholesale Price
-   Minimum Stock
-   Maximum Stock
-   Warehouse
-   Description
-   Notes

------------------------------------------------------------------------

## المخزون

-   إدارة المخزون
-   المخازن
-   مخزون كل مخزن
-   حركة المخزون
-   تحويل المخزون
-   الجرد
-   التسويات
-   المنتجات منخفضة المخزون
-   المنتجات غير المتوفرة

### حركة المخزون

عند وجودها في المنطق، تعرض:

-   Date
-   Product
-   Warehouse
-   Type
-   Quantity
-   Reference
-   User

الأنواع الممكنة:

-   Purchase
-   Sale
-   Return
-   Transfer
-   Adjustment

------------------------------------------------------------------------

## العملاء

-   قائمة العملاء
-   إضافة عميل
-   تعديل عميل
-   تفاصيل العميل
-   فواتير العميل
-   مدفوعات العميل
-   مرتجعات العميل
-   كشف حساب العميل

### كشف حساب العميل

يجب أن يكون محاسبيًا واضحًا:

-   Date
-   Reference
-   Description
-   Debit
-   Credit
-   Balance
-   Opening Balance
-   Closing Balance

مع:

-   Print
-   Export
-   Share

------------------------------------------------------------------------

## الموردون

-   قائمة الموردين
-   إضافة مورد
-   تعديل مورد
-   تفاصيل المورد
-   مشتريات المورد
-   مدفوعات المورد
-   المرتجعات
-   كشف الحساب

------------------------------------------------------------------------

## المدفوعات والسندات

عند وجود المنطق:

-   قائمة المدفوعات
-   سند قبض
-   سند صرف
-   دفع المورد
-   تحصيل العميل
-   تفاصيل العملية

وتعرض الجداول:

-   Date
-   Reference
-   Party
-   Amount
-   Method
-   Status
-   User

------------------------------------------------------------------------

## المصروفات

-   قائمة المصروفات
-   إضافة مصروف
-   تفاصيل المصروف
-   تصنيفات المصروفات

------------------------------------------------------------------------

## الصندوق

إذا كان موجودًا في المنطق:

-   نظرة عامة على الصندوق
-   حركة الصندوق
-   سند قبض
-   سند صرف
-   حركة مالية

------------------------------------------------------------------------

# 8. المحاسبة

إذا كان منطق المحاسبة موجودًا فعليًا في المشروع، يجب توفير واجهات له.

الشاشات الممكنة:

-   دليل الحسابات
-   تفاصيل الحساب
-   القيود اليومية
-   تفاصيل القيد
-   الأستاذ العام
-   ميزان المراجعة
-   الأرباح والخسائر
-   التدفقات النقدية

### مبدأ تصميم المحاسبة

المحاسبة تحتاج:

-   جداول
-   أرقام
-   Debit
-   Credit
-   Balance
-   Totals
-   Account Hierarchy

ولا تحتاج زخارف بصرية مبالغًا فيها.

------------------------------------------------------------------------

# 9. التقارير

يجب إنشاء Reports Center حسب التقارير الموجودة فعليًا في المشروع.

التصنيفات الممكنة:

-   Sales
-   Purchases
-   Inventory
-   Customers
-   Suppliers
-   Finance
-   Accounting

كل تقرير عند الحاجة يتكون من:

1.  عنوان التقرير.
2.  Filters.
3.  Summary.
4.  Chart.
5.  Detailed DataGrid.
6.  Totals.
7.  Print.
8.  Export.
9.  Share.

الفلاتر الممكنة:

-   Date Range
-   Warehouse
-   Customer
-   Supplier
-   Product
-   Category
-   User

ولا تتم إضافة تقرير غير مدعوم بالمنطق إلا كواجهة Preview واضحة.

------------------------------------------------------------------------

# 10. Dashboard

يجب أن يكون Dashboard مركز قيادة للأعمال وليس مجرد 4 Cards.

عند وجود البيانات المناسبة، يعرض:

-   Sales Today
-   Purchases Today
-   Gross/Net Profit
-   Cash Balance
-   Customer Receivables
-   Supplier Payables
-   Low Stock
-   Pending Transactions

وممكن أن يحتوي:

-   Sales Trend
-   Purchase Trend
-   Profit Trend
-   Recent Invoices
-   Recent Payments
-   Recent Activities
-   Low Stock Products
-   Customers with Outstanding Balances
-   Suppliers with Outstanding Balances
-   Quick Actions

------------------------------------------------------------------------

# 11. Windows UI --- المشكلة الحالية

التصميم الحالي للـ Windows غير مقبول.

الصورة المرجعية أظهرت مشاكل واضحة:

-   مساحة بيضاء ضخمة وغير مستغلة.
-   Sidebar بدائي.
-   عدم وجود Application Shell احترافي.
-   عدم وجود Top Bar مناسب.
-   عدم وجود Toolbar قوي.
-   عدم وجود DataGrid احترافي.
-   ضعف التسلسل البصري.
-   عدم وجود Dashboard فعلي.
-   عدم وجود Quick Actions.
-   عدم وجود Search/Filter System واضح.
-   عدم وجود حالات UI متكاملة.
-   التصميم يبدو كPrototype أو صفحة HTML بسيطة.
-   لا يعطي إحساس برنامج ERP تجاري حقيقي.
-   لا يستغل مساحة شاشة Windows.
-   لا توجد كثافة معلومات مناسبة لبرنامج محاسبي Desktop.

### القرار

لا يتم الاكتفاء بتجميل التصميم الحالي.

المطلوب:

# STRUCTURAL UI/UX REDESIGN

أي إعادة بناء تجربة Windows من الأساس مع المحافظة على الوظائف والمنطق
الموجودين.

------------------------------------------------------------------------

# 12. Windows App Shell

يجب بناء Shell احترافي يتضمن:

-   Logo
-   Company
-   Branch
-   Global Search
-   Notifications
-   Online/Offline Status
-   Sync Status
-   Current User
-   Sidebar
-   Top Bar
-   Breadcrumb
-   Page Header
-   Main Content
-   Status Bar عند الحاجة

------------------------------------------------------------------------

# 13. Windows Sidebar

الـ Sidebar يجب أن يكون Enterprise Navigation وليس قائمة نصوص فقط.

التقسيم المقترح:

## الرئيسية

-   لوحة التحكم

## المبيعات

-   نقطة البيع
-   الفواتير
-   المرتجعات
-   المبيعات الآجلة

## المشتريات

-   المشتريات
-   فواتير الشراء
-   المرتجعات

## المخزون

-   المنتجات
-   التصنيفات
-   المخازن
-   حركة المخزون
-   تحويل المخزون
-   الجرد
-   التسويات

## العملاء والموردون

-   العملاء
-   الموردون
-   كشوف الحساب

## المالية

-   المقبوضات
-   المدفوعات
-   المصروفات
-   الصندوق

## المحاسبة

-   دليل الحسابات
-   القيود اليومية
-   الأستاذ العام
-   ميزان المراجعة
-   الأرباح والخسائر

## التقارير

-   تقارير المبيعات
-   المشتريات
-   المخزون
-   العملاء
-   الموردين
-   المالية

## النظام

-   الإعدادات
-   المستخدمون
-   الصلاحيات
-   الطباعة
-   WhatsApp
-   المزامنة

### ملاحظة

لا تعرض أي قسم غير موجود فعليًا في المنطق أو الوثائق.

------------------------------------------------------------------------

# 14. Windows Top Bar

يجب ألا يكون مجرد عنوان.

يمكن أن يحتوي عند الحاجة على:

-   Page Title
-   Breadcrumb
-   Global Search
-   Quick Add
-   Notifications
-   Sync Status
-   Online/Offline Status
-   Current User
-   Branch

لا يتم حشر كل العناصر في كل شاشة.

------------------------------------------------------------------------

# 15. Page Header

كل شاشة يجب أن يكون لها Header واضح:

مثال:

``` text
المبيعات
إدارة ومتابعة جميع عمليات البيع والفواتير

[بحث] [تصفية] [تصدير] [طباعة] [+ فاتورة جديدة]
```

بدل عرض اسم الصفحة فقط.

------------------------------------------------------------------------

# 16. DataGrid

الجداول في Windows يجب أن تكون Enterprise Data Grids.

عند الحاجة تدعم:

-   Search
-   Filtering
-   Sorting
-   Column Resize
-   Sticky Header
-   Row Hover
-   Row Selection
-   Status Badge
-   Actions
-   Pagination
-   Horizontal Scroll

مثال أعمدة الفواتير:

-   رقم الفاتورة
-   العميل
-   التاريخ
-   المبلغ
-   المدفوع
-   المتبقي
-   الحالة
-   المستخدم
-   الإجراءات

------------------------------------------------------------------------

# 17. Forms

لا يتم وضع كل الحقول في Card ضخمة واحدة.

يجب تقسيم النموذج إلى Sections.

مثال المنتج:

## معلومات المنتج

-   اسم المنتج
-   SKU
-   Barcode
-   التصنيف
-   الماركة

## الأسعار

-   سعر التكلفة
-   سعر البيع
-   سعر الجملة

## المخزون

-   المخزن
-   الحد الأدنى
-   الحد الأعلى

## معلومات إضافية

-   الوصف
-   الملاحظات

------------------------------------------------------------------------

# 18. Details Pages

صفحات التفاصيل يجب أن تحتوي على:

-   Header
-   Summary
-   Actions
-   Tabs
-   Data
-   Timeline عند الحاجة

مثال العميل:

-   اسم العميل
-   الهاتف
-   العنوان
-   الرصيد
-   إجمالي المبيعات
-   إجمالي المدفوع
-   المستحق

Tabs:

-   نظرة عامة
-   الفواتير
-   المدفوعات
-   المرتجعات
-   كشف الحساب

------------------------------------------------------------------------

# 19. POS

نقطة البيع يجب أن تكون Desktop POS حقيقية.

التخطيط المقترح:

-   Search / Barcode
-   Categories
-   Product Grid
-   Current Cart
-   Customer
-   Discount
-   Payment
-   Totals
-   Complete Sale

ويجب أن تكون واجهتها مناسبة للماوس ولوحة المفاتيح.

------------------------------------------------------------------------

# 20. Invoice Details

Header:

-   Invoice Number
-   Date
-   Customer
-   Warehouse
-   Status

Items:

-   Product
-   SKU
-   Quantity
-   Price
-   Discount
-   Total

Summary:

-   Subtotal
-   Discount
-   Total
-   Paid
-   Due
-   Payment Method

Actions حسب المنطق:

-   View
-   Edit
-   Print
-   Preview
-   Share
-   Return
-   Cancel

------------------------------------------------------------------------

# 21. Print Preview

يجب أن تكون معاينة الطباعة قريبة من مستند حقيقي.

تحتوي عند الحاجة على:

-   Logo
-   Company Name
-   Address
-   Phone
-   Invoice Number
-   Date
-   Customer
-   Items
-   Subtotal
-   Discount
-   Total
-   Paid
-   Due
-   Footer

مع أدوات:

-   Printer
-   Paper Size
-   Copies
-   Margins
-   Print
-   Export PDF

------------------------------------------------------------------------

# 22. Product Management

قائمة المنتجات يجب أن تكون DataGrid احترافية.

الأعمدة الممكنة:

-   Product
-   SKU
-   Barcode
-   Category
-   Brand
-   Cost
-   Retail
-   Wholesale
-   Stock
-   Warehouse
-   Status
-   Actions

Toolbar:

-   Add Product
-   Import
-   Export
-   Search
-   Filters

فقط إذا كانت الوظائف موجودة.

------------------------------------------------------------------------

# 23. Product Details

يمكن تقسيمها إلى Tabs:

-   Overview
-   Pricing
-   Inventory
-   Suppliers
-   Sales
-   Purchases
-   Stock Movement

حسب المنطق.

------------------------------------------------------------------------

# 24. Inventory UI

نظرة المخزون تعرض عند الحاجة:

-   Total Products
-   Stock Value
-   Low Stock
-   Out of Stock

ثم جدول:

-   Product
-   Warehouse
-   Available
-   Reserved
-   Minimum
-   Cost
-   Value
-   Status

------------------------------------------------------------------------

# 25. Warehouses

الواجهات الممكنة:

-   Warehouse List
-   Warehouse Details
-   Warehouse Stock
-   Warehouse Transfers

------------------------------------------------------------------------

# 26. Stock Transfer

الشاشات:

-   Transfer List
-   New Transfer
-   Transfer Details

والواجهة تتضمن عند الحاجة:

-   From Warehouse
-   To Warehouse
-   Products
-   Quantity
-   Notes
-   Status

------------------------------------------------------------------------

# 27. Stock Adjustment

واجهة التسوية عند وجودها:

-   Product
-   Warehouse
-   Current Quantity
-   Adjusted Quantity
-   Difference
-   Reason
-   Date
-   User

------------------------------------------------------------------------

# 28. Customers

الواجهات:

-   Customer List
-   Add Customer
-   Edit Customer
-   Customer Details
-   Customer Statement
-   Customer Payments
-   Customer Invoices
-   Customer Returns

قائمة العملاء تعرض عند الحاجة:

-   Customer
-   Phone
-   Total Sales
-   Paid
-   Balance
-   Last Transaction
-   Status
-   Actions

------------------------------------------------------------------------

# 29. Suppliers

الواجهات:

-   Supplier List
-   Add Supplier
-   Edit Supplier
-   Supplier Details
-   Purchases
-   Payments
-   Returns
-   Statement

------------------------------------------------------------------------

# 30. Payments

الواجهات:

-   Payments List
-   Receive Payment
-   Pay Supplier
-   Payment Details

الأعمدة:

-   Date
-   Reference
-   Party
-   Amount
-   Method
-   Status
-   User

------------------------------------------------------------------------

# 31. Expenses

الواجهات:

-   Expense List
-   New Expense
-   Expense Details
-   Expense Categories

------------------------------------------------------------------------

# 32. Cash

إذا كان مدعومًا:

-   Cash Overview
-   Cash Transactions
-   Cash Receipt
-   Cash Payment
-   Cash Movement

------------------------------------------------------------------------

# 33. Settings

تقسيم الإعدادات المقترح:

-   Company
-   Users
-   Permissions
-   Appearance
-   Printers
-   Notifications
-   WhatsApp
-   Synchronization
-   Backup
-   Security
-   About

لكن يتم عرض الموجود فعليًا فقط.

------------------------------------------------------------------------

# 34. Users

إذا كان النظام يدعم المستخدمين:

-   Users List
-   Add User
-   Edit User
-   User Details
-   User Activity

------------------------------------------------------------------------

# 35. Permissions

إذا كانت موجودة:

الأقسام:

-   Sales
-   Purchases
-   Inventory
-   Customers
-   Suppliers
-   Reports
-   Accounting
-   Settings

والصلاحيات:

-   View
-   Create
-   Edit
-   Delete
-   Print
-   Export

------------------------------------------------------------------------

# 36. Notifications

إذا كانت موجودة:

-   Notification Center
-   Notification Details

حالات ممكنة:

-   Low Stock
-   Payment Due
-   System
-   Sync

------------------------------------------------------------------------

# 37. UI States

كل شاشة يجب أن تحتوي على الحالات المناسبة:

-   Loading
-   Empty
-   Populated
-   Searching
-   Filtering
-   Error
-   Success
-   Offline
-   Disabled
-   Permission Restricted

------------------------------------------------------------------------

# 38. Offline UI

يجب أن تكون حالة Offline واضحة ولكن غير مزعجة.

أمثلة:

``` text
● Online
```

``` text
● Offline
Last Sync: 10:32 AM
```

``` text
↻ Syncing
```

``` text
⚠ Sync Failed
```

------------------------------------------------------------------------

# 39. Sync Center

إذا كان موجودًا في المشروع:

-   Connection Status
-   Last Sync
-   Pending Changes
-   Failed Changes
-   Device
-   Server

أزرار UI:

-   Sync Now
-   Retry
-   View Details

لا يتم تنفيذ Sync Engine داخل UI.

------------------------------------------------------------------------

# 40. Global Search

يجب توفير بحث شامل عند وجود وظائفه.

يمكن أن يبحث في:

-   Products
-   Customers
-   Suppliers
-   Invoices
-   Purchases
-   Transactions

ولا يتم إنشاء Backend جديد.

------------------------------------------------------------------------

# 41. Keyboard UX

لأن Windows Desktop:

يمكن تجهيز UI لاختصارات مثل:

-   Ctrl + K = Search
-   Ctrl + N = New
-   Ctrl + S = Save
-   Ctrl + P = Print
-   Esc = Close
-   F2 = Edit
-   F4 = Payment
-   F8 = Complete Sale

لكن لا يتم تنفيذ اختصار لوظيفة غير موجودة.

------------------------------------------------------------------------

# 42. Context Menus

يجب استخدام Right Click في الأماكن المناسبة.

Invoice:

-   View
-   Edit
-   Print
-   Share
-   Return
-   Cancel

Product:

-   View
-   Edit
-   Stock Movement
-   Print

Customer:

-   View
-   Statement
-   New Invoice

فقط حسب الوظائف الحقيقية.

------------------------------------------------------------------------

# 43. Design System

يجب إنشاء Design System موحد للـ Windows.

يشمل:

-   Colors
-   Typography
-   Spacing
-   Radius
-   Borders
-   Buttons
-   Inputs
-   Tables
-   DataGrid
-   Dialogs
-   Sidebar
-   TopBar
-   Tabs
-   Badges
-   Charts
-   Notifications

------------------------------------------------------------------------

# 44. Typography

استخدم خط عربي احترافي مناسب لـ Desktop.

يجب أن يكون:

-   واضحًا
-   مقروءًا
-   مناسبًا للأرقام
-   ممتازًا للجداول
-   مناسبًا للعناوين

------------------------------------------------------------------------

# 45. الألوان

لا تجعل النظام:

-   أبيض بالكامل
-   أسود بالكامل
-   أزرق بالكامل
-   ملونًا بشكل مبالغ

استخدم لون هوية رئيسي مع:

-   Success
-   Warning
-   Error
-   Info

بشكل متوازن.

------------------------------------------------------------------------

# 46. Dark Mode

يجب أن يكون Dark Mode حقيقيًا عند دعم النظام له.

يجب التفريق بين:

-   Background
-   Surface
-   Elevated Surface
-   Border
-   Primary Text
-   Secondary Text
-   Muted Text

------------------------------------------------------------------------

# 47. RTL

كل النظام عربي RTL.

يجب مراجعة:

-   Sidebar
-   Top Bar
-   Tables
-   Forms
-   Dialogs
-   Menus
-   Breadcrumbs
-   Numbers
-   Dates
-   Icons
-   Alignment

------------------------------------------------------------------------

# 48. Responsive Desktop

يجب أن يعمل Windows UI بشكل جيد على الأقل على:

-   1280×720
-   1366×768
-   1600×900
-   1920×1080
-   الشاشات الأكبر

عند تصغير النافذة:

-   Sidebar يمكن أن ينهار
-   الجداول تسمح بالتمرير الأفقي
-   Panels تتكيف
-   لا تختفي الأزرار المهمة

------------------------------------------------------------------------

# 49. Information Density

البرنامج ERP وليس Landing Page.

يجب منع:

-   المساحات البيضاء الضخمة
-   Cards العملاقة
-   الأزرار المتضخمة
-   الزخارف الزائدة

المطلوب:

# HIGH INFORMATION DENSITY + EXCELLENT READABILITY

------------------------------------------------------------------------

# 50. Professional UX

كل شاشة يجب أن تجيب بوضوح:

1.  أين أنا؟
2.  ماذا أرى؟
3.  ماذا أستطيع أن أفعل؟
4.  ما الإجراء الرئيسي؟
5.  كيف أرجع؟
6.  ما حالة العملية؟

------------------------------------------------------------------------

# 51. لا تجعل كل شيء Button

استخدم حسب الحاجة:

-   Toolbar
-   Tabs
-   Dropdown
-   Context Menu
-   Overflow Menu
-   Quick Actions
-   Keyboard Shortcuts

------------------------------------------------------------------------

# 52. Animation

استخدم Animation بسيطًا واحترافيًا فقط:

-   Hover
-   Transition
-   Sidebar
-   Modal
-   Loading

بدون Animation مبالغ فيه.

------------------------------------------------------------------------

# 53. UI Architecture

التنظيم المقترح:

``` text
App Shell
 ├── Navigation
 ├── Top Bar
 ├── Page Header
 ├── Content
 └── Status Bar

Features
 ├── Sales
 ├── Purchases
 ├── Inventory
 ├── Customers
 ├── Suppliers
 ├── Finance
 ├── Accounting
 ├── Reports
 └── Settings
```

------------------------------------------------------------------------

# 54. Shared Components

يجب إعادة استخدام:

-   AppShell
-   Sidebar
-   TopBar
-   PageHeader
-   Toolbar
-   SearchBar
-   FilterBar
-   DataGrid
-   StatusBadge
-   KPI
-   Chart
-   EmptyState
-   LoadingState
-   ErrorState
-   ConfirmDialog
-   FormSection
-   MoneyDisplay
-   DateDisplay

لا تكرر نفس المكونات في كل شاشة.

------------------------------------------------------------------------

# 55. قواعد العمل مع المنطق الحالي

يجب فحص:

-   Business Logic
-   ViewModels
-   Models
-   UseCases
-   Repositories
-   API Contracts
-   Database Entities
-   Navigation
-   Services
-   Documentation
-   Android UI
-   React/TSX UI
-   Windows UI

وذلك لمعرفة:

> ما هي الوظائف التي تحتاج واجهة.

------------------------------------------------------------------------

# 56. ممنوع تغيير المنطق

هذه المهمة UI/UX بالدرجة الأولى.

لا يتم تغيير أو إعادة كتابة:

-   Business Logic
-   Accounting Logic
-   Inventory Logic
-   Database Logic
-   API
-   Backend
-   Authentication Logic
-   Sync Engine
-   Payment Engine
-   Repository Logic
-   UseCase Logic

ولا يتم اختراع API جديدة.

ولا يتم اختراع Business Rules جديدة.

------------------------------------------------------------------------

# 57. بيانات المعاينة

يمكن استخدام Preview/Mock Data فقط عند الحاجة لإظهار التصميم، مثل:

### Products

-   MikroTik hEX S
-   TP-Link Archer C6
-   Ubiquiti NanoStation 5AC
-   D-Link 8-Port Switch
-   Cat6 UTP Cable
-   RJ45 Connector

### Customers

-   مؤسسة النور للاتصالات
-   شبكات المستقبل
-   محلات التقنية الحديثة

ويجب أن تكون البيانات واقعية وواضحة.

------------------------------------------------------------------------

# 58. Project Audit

قبل تنفيذ التصميم يجب فحص المشروع كاملًا.

ابحث عن:

``` text
features/
screens/
components/
ViewModels/
Models/
UseCases/
Repositories/
Services/
Navigation/
Database/
API/
docs/
```

ثم استخرج كل العمليات.

------------------------------------------------------------------------

# 59. Function Map

يجب إنشاء:

`WINDOWS_UI_FUNCTION_MAP.md`

يحتوي على:

  Feature     Logic Exists   Windows UI   Required Screens   Status
  ----------- -------------- ------------ ------------------ ----------
  Sales       YES            YES          ...                Complete
  Inventory   YES            NO           ...                Missing

أي:

`Logic = YES + UI = NO`

يعني أن هناك UI ناقصة ويجب بناؤها.

------------------------------------------------------------------------

# 60. Screen Inventory

يجب إنشاء:

`WINDOWS_SCREEN_INVENTORY.md`

يحتوي جميع:

-   Main Screens
-   Detail Screens
-   Create Screens
-   Edit Screens
-   Reports
-   Dialogs
-   Print Views
-   Selection Windows
-   Settings Pages

لا تفترض عددًا ثابتًا للشاشات.

العدد النهائي يجب أن ينتج من الفحص الحقيقي للمشروع.

------------------------------------------------------------------------

# 61. Navigation Map

أنشئ:

`WINDOWS_NAVIGATION_MAP.md`

يوضح:

-   كل شاشة
-   من أين تفتح
-   أين تؤدي
-   Back behavior
-   Dialogs
-   Details
-   Create/Edit flows

------------------------------------------------------------------------

# 62. Design System Documentation

أنشئ:

`WINDOWS_DESIGN_SYSTEM.md`

يوضح:

-   Colors
-   Typography
-   Spacing
-   Radius
-   Components
-   DataGrid
-   Forms
-   Dialogs
-   Navigation
-   States

------------------------------------------------------------------------

# 63. Component Map

أنشئ:

`WINDOWS_COMPONENT_MAP.md`

يوضح Shared Components وإعادة استخدامها.

------------------------------------------------------------------------

# 64. UI Coverage Report

أنشئ:

`WINDOWS_UI_COVERAGE_REPORT.md`

يتضمن:

-   Total Functions Found
-   Total Windows Screens Required
-   Existing Screens
-   New Screens
-   Rebuilt Screens
-   Dialogs
-   Print Views
-   Report Views
-   Settings Pages
-   UI Coverage
-   Design System
-   Navigation
-   RTL
-   Dark Mode
-   Responsive
-   Keyboard UX
-   Loading States
-   Empty States
-   Error States
-   Offline UI
-   Build
-   Runtime
-   Remaining Missing UI

------------------------------------------------------------------------

# 65. مراحل تنفيذ Windows

## PHASE 1

-   Application Shell
-   Design System
-   Sidebar
-   TopBar
-   Navigation
-   Theme

## PHASE 2

-   Dashboard

## PHASE 3

-   Sales
-   POS
-   Invoices

## PHASE 4

-   Products
-   Inventory
-   Warehouses

## PHASE 5

-   Customers
-   Suppliers

## PHASE 6

-   Purchases
-   Returns
-   Payments
-   Expenses

## PHASE 7

-   Accounting

## PHASE 8

-   Reports

## PHASE 9

-   Settings
-   Users
-   Permissions
-   Printing
-   WhatsApp
-   Sync UI

إذا كشف الـ Audit أن ترتيبًا آخر أفضل، استخدم الترتيب المناسب.

------------------------------------------------------------------------

# 66. معيار الجودة

لن يتم قبول واجهة تبدو كـ:

-   Prototype
-   Demo
-   HTML Page
-   Android UI مكبرة
-   Dashboard Template
-   صفحة فارغة
-   مجموعة Cards
-   Form بدائي

المطلوب:

# ENTERPRISE WINDOWS APPLICATION

------------------------------------------------------------------------

# 67. معيار إعادة التصميم

لا تعتبر المهمة مكتملة بمجرد:

-   تغيير اللون
-   تغيير الخط
-   إضافة Cards
-   تعديل Button
-   إضافة Shadow

إذا كان الهيكل ضعيفًا يجب إعادة بناء:

-   Layout
-   Navigation
-   Information Architecture
-   Components
-   Data Presentation
-   Workflow
-   States
-   Desktop UX

------------------------------------------------------------------------

# 68. منهج التنفيذ

استخدم:

# ANALYZE → RESTRUCTURE → REDESIGN → IMPLEMENT → VERIFY

### الخطوة 1

Audit كامل للمشروع.

### الخطوة 2

استخراج جميع الوظائف.

### الخطوة 3

مطابقة كل وظيفة مع Windows UI.

### الخطوة 4

تحديد الشاشات والـ Dialogs والـ Forms والـ Print Views الناقصة.

### الخطوة 5

إعادة بناء Application Shell.

### الخطوة 6

إنشاء Design System.

### الخطوة 7

إعادة بناء Dashboard.

### الخطوة 8

إعادة بناء Sales/POS.

### الخطوة 9

إعادة بناء Purchases.

### الخطوة 10

إعادة بناء Inventory.

### الخطوة 11

إعادة بناء Customers/Suppliers.

### الخطوة 12

إعادة بناء Finance/Accounting.

### الخطوة 13

إعادة بناء Reports.

### الخطوة 14

إعادة بناء Settings/System.

### الخطوة 15

إضافة Dialogs / Forms / Print Views.

### الخطوة 16

اختبار RTL.

### الخطوة 17

اختبار Desktop Responsive.

### الخطوة 18

Visual QA.

### الخطوة 19

Final UI Coverage Audit.

------------------------------------------------------------------------

# 69. قاعدة نهائية شديدة الأهمية

المطلوب ليس مجرد إضافة شاشات.

المطلوب أن تكون:

> جميع الوظائف الموجودة في منطق المشروع لها واجهة Windows مناسبة
> ومتكاملة.

ولا يتم اعتبار المشروع مكتملًا إذا كان:

``` text
Logic = YES
UI = NO
```

------------------------------------------------------------------------

# 70. حدود مسؤولية UI

إذا كانت المهمة مخصصة للواجهات فقط:

يمكن قراءة المنطق لفهم الوظائف، لكن لا يتم تحويل العمل إلى تطوير
Business Logic.

لا يتم:

-   بناء محرك محاسبي جديد
-   بناء محرك مخزون جديد
-   إعادة بناء API
-   إعادة بناء Sync Engine
-   إعادة بناء Database
-   تغيير قواعد الحساب

الهدف:

# COMPLETE PROFESSIONAL UI COVERAGE

------------------------------------------------------------------------

# 71. النتيجة النهائية المطلوبة

أريد تطبيقًا:

-   احترافيًا
-   حديثًا
-   واضحًا
-   سريعًا
-   RTL
-   Desktop-first في Windows
-   Mobile-first في Android
-   كثيف المعلومات دون ازدحام
-   قابلًا للطباعة
-   جاهزًا للـ Offline UI
-   جاهزًا للـ Sync UI
-   جاهزًا لمشاركة WhatsApp
-   متناسقًا في جميع الشاشات
-   مبنيًا وفق Design System موحد

والأهم:

> لا يبدو كواجهة مولدة آليًا أو Prototype.

بل:

# PRODUCTION-QUALITY ENTERPRISE ERP UI

------------------------------------------------------------------------

# 72. المرجع البصري الحالي

الصورة التي تم تقييمها في المحادثة أظهرت واجهة Windows ذات:

-   Sidebar داكن على اليمين.
-   مساحة بيضاء كبيرة جدًا.
-   نصوص بسيطة.
-   محتوى قليل.
-   غياب واضح للتسلسل البصري.
-   غياب DataGrid وDashboard حقيقي.
-   غياب Top Bar وToolbar احترافي.
-   إحساس قوي بأنها Prototype.

لذلك يجب اعتبار التصميم الحالي نقطة تحتاج إلى إعادة بناء UX، وليس مجرد
نقطة بداية لتغيير الألوان.

------------------------------------------------------------------------

# 73. القرار النهائي

## لا نريد:

"تحسين الواجهة الحالية قليلًا."

## نريد:

"إعادة بناء Windows UI/UX بالكامل وفق معايير Enterprise Desktop ERP، مع
المحافظة على المنطق والـ API والـ Database والوظائف الحالية."

------------------------------------------------------------------------

# FINAL COMMAND

ابدأ دائمًا بـ:

1.  Audit.
2.  Function Extraction.
3.  Screen Mapping.
4.  UX Architecture.
5.  Design System.
6.  App Shell.
7.  Screen Implementation.
8.  States.
9.  Responsive Desktop.
10. RTL.
11. Visual QA.
12. Coverage Audit.

ولا تعلن اكتمال المشروع قبل التأكد من أن كل وظيفة موجودة في المنطق لها
واجهة مناسبة.
