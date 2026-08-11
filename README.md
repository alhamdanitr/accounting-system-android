# Accounting System - Android App

تطبيق الأندرويد الرسمي لنظام إدارة المحلات والمحاسبة والمخزون، مبني بلغة **Kotlin** واستخدام **Jetpack Compose**، ومصمم للعمل بنظام **Offline-First** مع مزامنة ذكية عبر الخادم المركزي (Backend).

## إنجازات التطبيق
- **Clean Architecture:** تقسيم الطبقات إلى Domain, Data, و UI.
- **Offline-First:** دعم قاعدة البيانات المحلية عبر **Room** لضمان استمرار العمل بلا إنترنت.
- **Sync Engine:** محرك مزامنة للربط مع الخادم المركزي (Backend Push/Pull).
- **Jetpack Compose UI:** واجهات مستخدم حديثة ومتكاملة تشمل شاشة نقاط البيع (POS)، إدارة المخزون، المحاسبة، ولوحة التحكم (Dashboard).

## التقنيات المستخدمة
- **Language:** Kotlin 100%
- **UI Toolkit:** Jetpack Compose & Material 3
- **Local Database:** Room (SQLite)
- **Network:** Retrofit & OkHttp
- **Architecture Components:** ViewModel & StateFlow

## المستودع الرسمي
[GitHub Repository](https://github.com/alhamdanitr/accounting-system-android)
