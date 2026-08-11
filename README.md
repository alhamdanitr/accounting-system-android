# Accounting System - Android App

تطبيق الأندرويد الرسمي لنظام إدارة المحلات والمحاسبة، مبني بلغة **Kotlin** واستخدام **Jetpack Compose**، ومصمم للعمل بنظام **Offline-First** مع مزامنة ذكية عبر الخادم المركزي (Backend).

## التقنيات المستخدمة
- **Language:** Kotlin 100%
- **UI Toolkit:** Jetpack Compose & Material 3
- **Architecture:** Clean Architecture (Domain, Data, UI)
- **Local Database:** Room (SQLite) for Offline support
- **Network / Sync:** Retrofit, OkHttp, WorkManager for background sync
- **Dependency Injection:** Hilt

## هيكل المشروع
- `data/`: قواعد البيانات المحلية Room، خدمات API، ومستودعات البيانات ومحرك المزامنة المحلي.
- `domain/`: نماذج البيانات التجارية، واجهات المستودعات، وحالات الاستخدام (Use Cases).
- `ui/`: شاشات Jetpack Compose، نماذج العرض (ViewModels)، ونظام التنقل.
- `di/`: وحدات حقن التبعيات (Hilt Modules).

## المستودع الرسمي
[GitHub Repository](https://github.com/alhamdanitr/accounting-system-android)
