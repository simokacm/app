# كيفية استخراج ملف APK جاهز للتثبيت (بدون الحاجة لتثبيت برامج على حاسوبك)

بما أن ملف `.apk` يحتاج إلى مترجم أندرويد الرسمي (Android SDK Build-Tools)، فقد أضفنا داخل هذا المشروع إعداداً سحابياً جاهزاً (`.github/workflows/build-apk.yml`) يبني لك ملف **APK** تلقائياً ومجاناً عبر GitHub Actions:

### الطريقة الأولى: استخراج APK عبر الهاتف أو المتصفح مجاناً (بدون Android Studio)
1. أنشئ مستودعاً جديداً (Repository) على حسابك في **GitHub**.
2. ارفع محتويات مجلد `MindfulLockAndroid` إلى المستودع.
3. انتقل إلى تبويب **Actions** في المستودع، وستجد عملية **Build MindfulLock APK** تعمل تلقائياً.
4. بعد انتهائها (خلال دقيقتين)، اضغط على **MindfulLock-French-APK** لتحميل ملف `app-debug.apk` وتثبيته مباشرة على أي هاتف أندرويد.

### الطريقة الثانية: عبر برنامج Android Studio على الحاسوب
1. افتح مجلد `MindfulLockAndroid` داخل **Android Studio**.
2. من القائمة العلوية اختر: **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
3. سيظهر لك ملف `app-debug.apk` جاهزاً للنقل والتثبيت على هاتفك.
