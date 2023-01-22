SET @country_id = UUID();
INSERT IGNORE INTO `countries`(`id`, `name`) VALUES (@country_id, 'ایران');

--
-- Guilan
--
SET @guilan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@guilan_id, 'گیلان', @country_id);
--
-- Cities of Guilan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'صومعه سرا', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رشت', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بندر انزلی', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'لاهیجان', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تالش', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رودسر', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'لنگرود', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آستانه اشرفیه', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رودبار', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آستارا', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رضوانشهر', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شفت', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خمام', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ماسال', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سیاهکل', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'املش', @guilan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فومن', @guilan_id);

--
-- Tehran
--
SET @tehran_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@tehran_id, 'تهران', @country_id);
--
-- Cities of Tehran
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تهران', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شهریار', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اسلامشهر', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بهارستان', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ملارد', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'پاکدشت', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ری', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'قدس', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رباط کریم', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ورامین', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'قرچک', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'پردیس', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دماوند', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'پیشوا', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شمیرانات', @tehran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فیروزکوه', @tehran_id);

--
-- Alborz
--
SET @alborz_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@alborz_id, 'کرج', @country_id);
--
-- Cities of Alborz
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کرج', @alborz_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فردیس', @alborz_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ساوجبلاغ', @alborz_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نظرآباد', @alborz_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'چهارباغ', @alborz_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اشتهارد', @alborz_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'طالقان', @alborz_id);

--
-- Mazandaran
--
SET @mazandaran_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@mazandaran_id, 'مازندران', @country_id);
--
-- Cities of Mazandaran
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ساری', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گلوگاه', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بهشهر', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نکا', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'میان دورود', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'جویبار', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سیمرغ', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'قائم شهر', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سوادکوه شمالی', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سوادکوه', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بابلسر', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بابل', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فریدون کنار', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'محمود آباد', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آمل', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نور', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نوشهر', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'چالوس', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کلاردشت', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'عباس آباد', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تنکابن', @mazandaran_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رامسر', @mazandaran_id);

--
-- Golestan
--
SET @golestan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@golestan_id, 'گلستان', @country_id);
--
-- Cities of Golestan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گرگان', @golestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گنبد کاووس', @golestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ترکمن', @golestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'علی آباد', @golestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آزادشهر', @golestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کردکوی', @golestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کلاله', @golestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آق قلا', @golestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مینودشت', @golestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گالیکش', @golestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بندر گز', @golestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رامیان', @golestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مراوه تپه', @golestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گمیشان', @golestan_id);

--
-- Ardebil
--
SET @ardebil_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@ardebil_id, 'اردبیل', @country_id);
--
-- Cities of Ardebil
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اردبیل', @ardebil_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مشگین شهر', @ardebil_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'پارس آباد', @ardebil_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خلخال', @ardebil_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نمین', @ardebil_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گرمی', @ardebil_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بیله سوار', @ardebil_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اصلاندوز', @ardebil_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نیر', @ardebil_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کوثر', @ardebil_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'انگوت', @ardebil_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سرعین', @ardebil_id);

--
-- Zanjan
--
SET @zanjan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@zanjan_id, 'زنجان', @country_id);
--
-- Cities of Zanjan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'زنجان', @zanjan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ابهر', @zanjan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خدابنده', @zanjan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خرمدره', @zanjan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'طارم', @zanjan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ماهنشان', @zanjan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ایجرود', @zanjan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سلطانیه', @zanjan_id);

--
-- Markazi
--
SET @markazi_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@markazi_id, 'اراک', @country_id);
--
-- Cities of Markazi
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اراک', @markazi_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ساوه', @markazi_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خمین', @markazi_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'محلات', @markazi_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دلیجان', @markazi_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شازند', @markazi_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'زرندیه', @markazi_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کمیجان', @markazi_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آشتیان', @markazi_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بازنه', @markazi_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تفرش', @markazi_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خنداب', @markazi_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فراهان', @markazi_id);

--
-- East-Azerbaijan
--
SET @east_azerbaijan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@east_azerbaijan_id, 'آذربایجان شرقی', @country_id);
--
-- Cities of East-Azerbaijan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تبریز', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آذرشهر', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اسکو', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اهر', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بستان آباد', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بناب', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'جلفا', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'چاراویماق', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خداآفرین', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سراب', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شبستر', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'عجب شیر', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کلیبر', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مراغه', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مرند', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ملکان', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'میانه', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ورزقان', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'هریس', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'هشترود', @east_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'هوراند', @east_azerbaijan_id);

--
-- West-Azerbaijan
--
SET @west_azerbaijan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@west_azerbaijan_id, 'آذربایجان غربی', @country_id);
--
-- Cities of West-Azerbaijan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ارومیه', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خوی', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بوکان', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مهاباد', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'میاندوآب', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سلماس', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'پیرانشهر', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نقده', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تکاب', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ماکو', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سردشت', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شاهین دژ', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اشنویه', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'چایپاره', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شوط', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'چالدران', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'چهاربرج', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'باروق', @west_azerbaijan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'پلدشت', @west_azerbaijan_id);

--
-- Kurdistan
--
SET @kurdistan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@kurdistan_id, 'کردستان', @country_id);
--
-- Cities of Kurdistan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سنندج', @kurdistan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سقز', @kurdistan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مریوان', @kurdistan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بانه', @kurdistan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'قروه', @kurdistan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کامیاران', @kurdistan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بیجار', @kurdistan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دیواندره', @kurdistan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دهگلان', @kurdistan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سروآباد', @kurdistan_id);

--
-- Kermanshah
--
SET @kermanshah_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@kermanshah_id, 'کرمانشاه', @country_id);
--
-- Cities of Kermanshah
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کرمانشاه', @kermanshah_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اسلام آباد غرب', @kermanshah_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سرپل ذهاب', @kermanshah_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سنقر', @kermanshah_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'هرسین', @kermanshah_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کنگاور', @kermanshah_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'جوانرود', @kermanshah_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'صحنه', @kermanshah_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'پاوه', @kermanshah_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گیلانغرب', @kermanshah_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'روانسر', @kermanshah_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دالاهو', @kermanshah_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ثلاث باباجانی', @kermanshah_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'قصرشیرین', @kermanshah_id);

--
-- Ilam
--
SET @ilam_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@ilam_id, 'ایلام', @country_id);
--
-- Cities of Ilam
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ایلام', @ilam_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دهلران', @ilam_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'چرداول', @ilam_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ایوان', @ilam_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آبدانان', @ilam_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دره شهر', @ilam_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مهران', @ilam_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ملکشاهی', @ilam_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بدره', @ilam_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'هلیلان', @ilam_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سیروان', @ilam_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'چوار', @ilam_id);

--
-- Khuzestan
--
SET @khuzestan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@khuzestan_id, 'خوزستان', @country_id);
--
-- Cities of Khuzestan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اهواز', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آبادان', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آغاجاری', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'امیدیه', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اندیکا', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اندیمشک', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ایذه', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'باغ ملک', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'باوی', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بندر ماهشهر', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بهبهان', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'حمیدیه', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خرمشهر', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دزفول', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دشت آزادگان', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رامشیر', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رامهرمز', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شادگان', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شوش', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شوشتر', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کارون', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گتوند', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'لالی', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مسجد سلیمان', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'هفتکل', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'هندیجان', @khuzestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'هویزه', @khuzestan_id);

--
-- Lorestan
--
SET @lorestan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@lorestan_id, 'لرستان', @country_id);
--
-- Cities of Lorestan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خرم آباد', @lorestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بروجرد', @lorestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دورود', @lorestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کوهدشت', @lorestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دلفان', @lorestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'الیگودرز', @lorestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سلسله', @lorestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ازنا', @lorestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'پلدختر', @lorestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'چگنی', @lorestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رومشکان', @lorestan_id);

--
-- Kohgiluie-Va-Boyerahmad
--
SET @kohgiluie_va_boyerahmad_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@kohgiluie_va_boyerahmad_id, 'کهگیلویه و بویراحمد', @country_id);
--
-- Cities of Kohgiluie-Va-Boyerahmad
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بویراحمد', @kohgiluie_va_boyerahmad_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کهگیلویه', @kohgiluie_va_boyerahmad_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گچساران', @kohgiluie_va_boyerahmad_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دنا', @kohgiluie_va_boyerahmad_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بهمئی', @kohgiluie_va_boyerahmad_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'چرام', @kohgiluie_va_boyerahmad_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'باشت', @kohgiluie_va_boyerahmad_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'لنده', @kohgiluie_va_boyerahmad_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مارگون', @kohgiluie_va_boyerahmad_id);

--
-- Chaharmahal-Va-Bakhtiyary
--
SET @chaharmahal_va_bakhtiyary_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@chaharmahal_va_bakhtiyary_id, 'چهارمحال و بختیاری', @country_id);
--
-- Cities of Chaharmahal-Va-Bakhtiyary
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شهرکرد', @chaharmahal_va_bakhtiyary_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'لردگان', @chaharmahal_va_bakhtiyary_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بروجن', @chaharmahal_va_bakhtiyary_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فارسان', @chaharmahal_va_bakhtiyary_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خانمیرزا', @chaharmahal_va_bakhtiyary_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کیار', @chaharmahal_va_bakhtiyary_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اردل', @chaharmahal_va_bakhtiyary_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کوهرنگ', @chaharmahal_va_bakhtiyary_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سامان', @chaharmahal_va_bakhtiyary_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بن', @chaharmahal_va_bakhtiyary_id);

--
-- Bushehr
--
SET @bushehr_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@bushehr_id, 'بوشهر', @country_id);
--
-- Cities of Bushehr
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بوشهر', @bushehr_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دشتستان', @bushehr_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کنگان', @bushehr_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گناوه', @bushehr_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دشتی', @bushehr_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تنگستان', @bushehr_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'عسلویه', @bushehr_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'جم', @bushehr_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دیر', @bushehr_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دیلم', @bushehr_id);

--
-- Hormozgan
--
SET @hormozgan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@hormozgan_id, 'هرمزگان', @country_id);
--
-- Cities of Hormozgan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بندرعباس', @hormozgan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'میناب', @hormozgan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بندرلنگه', @hormozgan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'قشم', @hormozgan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رودان', @hormozgan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بستک', @hormozgan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'حاجی آباد', @hormozgan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'جاسک', @hormozgan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خمیر', @hormozgan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'پارسیان', @hormozgan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سیربک', @hormozgan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بشاگرد', @hormozgan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ابوموسی', @hormozgan_id);

--
-- Sistan-Va-Baluchestan
--
SET @sistan_va_baluchestan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@sistan_va_baluchestan_id, 'سیستان و بلوچستان', @country_id);
--
-- Cities of Sistan-Va-Baluchestan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'زاهدان', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ایرانشهر', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'چابهار', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'زابل', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سراوان', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نیکشهر', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خاش', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'راسک', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سرباز', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سیب و سوران', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دشتیاری', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کنارک', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'زهک', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مهرستان', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فنوج', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دلگان', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'هیرمند', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'قصرقند', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بمپور', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نیمروز', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'میرجاوه', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تفتان', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'هامون', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'لاشار', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گلشن', @sistan_va_baluchestan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'زرآباد', @sistan_va_baluchestan_id);

--
-- Kerman
--
SET @kerman_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@kerman_id, 'کرمان', @country_id);
--
-- Cities of Kerman
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کرمان', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سیرجان', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رفسنجان', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'جیرفت', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بم', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'زرند', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رودبار جنوب', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شهربابک', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کهنوج', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ریگان', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بافت', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'عنبرآباد', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بردسیر', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'قلعه گنج', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فهرج', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'منوجان', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نرماشیر', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'راور', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ارزوئیه', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'انار', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رابر', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فاریاب', @kerman_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کوهبنان', @kerman_id);

--
-- Fars
--
SET @fars_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@fars_id, 'فارس', @country_id);
--
-- Cities of Fars
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شیراز', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آباده', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ارسنجان', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'استهبان', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اقلید', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اوز', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بختگان', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بوانات', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بیضا', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'پاسارگاد', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'جویم', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'جهرم', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خرامه', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خرم بید', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خفر', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خنج', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'داراب', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رستم', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'زرقان', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'زرین دشت', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سپیدان', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سرچهان', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سروستان', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فراشبند', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فسا', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فیروزآباد', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'قیر وکارزین', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کازرون', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کوار', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کوه چنار', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گراش', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'لارستان', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'لامرد', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مرودشت', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ممسنی', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مهر', @fars_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نی ریز', @fars_id);

--
-- Isfahan
--
SET @isfahan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@isfahan_id, 'اصفهان', @country_id);
--
-- Cities of Isfahan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اصفهان', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آران و بیدگل', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اردستان', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'برخوار', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بوئین و میاندشت', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تیران و کرون', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'جرقویه', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'چادگان', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خمینی شهر', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خوانسار', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خور و بیابانک', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دهاقان', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سمیرم', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شاهین شهر و میمه', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شهرضا', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فریدن', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فریدون شهر', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فلاورجان', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کاشان', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کوهپایه', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گلپایگان', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'لنجان', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مبارکه', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نائین', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نجف آباد', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نطنز', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ورزنه', @isfahan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'هرند', @isfahan_id);

--
-- Yazd
--
SET @yazd_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@yazd_id, 'یزد', @country_id);
--
-- Cities of Yazd
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'یزذ', @yazd_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'میبد', @yazd_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اردکان', @yazd_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مهریز', @yazd_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ابرکوه', @yazd_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بافق', @yazd_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تفت', @yazd_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اشکذر', @yazd_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خاتم', @yazd_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بهاباد', @yazd_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مروست', @yazd_id);

--
-- Semnan
--
SET @semnan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@semnan_id, 'سمنان', @country_id);
--
-- Cities of Semnan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سمنان', @semnan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آرادان', @semnan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'دامغان', @semnan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سرخه', @semnan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شاهرود', @semnan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گرمسار', @semnan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مهدیشهر', @semnan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'میامی', @semnan_id);

--
-- Qazvin
--
SET @qazvin_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@qazvin_id, 'قزوین', @country_id);
--
-- Cities of Qazvin
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'قزوین', @qazvin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تاکستان', @qazvin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'البرز', @qazvin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بوئین زهرا', @qazvin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آبیک', @qazvin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'آوج', @qazvin_id);

--
-- Hamadan
--
SET @hamadan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@hamadan_id, 'همدان', @country_id);
--
-- Cities of Hamadan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'همدان', @hamadan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ملایر', @hamadan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نهاوند', @hamadan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کبودراهنگ', @hamadan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بهار', @hamadan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رزن', @hamadan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تویسرکان', @hamadan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اسدآباد', @hamadan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فامنین', @hamadan_id);

--
-- North-Khorasan
--
SET @north_khorasan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@north_khorasan_id, 'خراسان شمالی', @country_id);
--
-- Cities of North-Khorasan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بجنورد', @north_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'اسفراین', @north_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'جاجرم', @north_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'راز و جرگلان', @north_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'شیروان', @north_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فاروج', @north_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گرمه', @north_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مانه و سملقان', @north_khorasan_id);

--
-- Razavi-Khorasan
--
SET @razavi_khorasan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@razavi_khorasan_id, 'خراسان رضوی', @country_id);
--
-- Cities of Razavi-Khorasan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مشهد', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'باخرز', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بجستان', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بردسکن', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بینالود', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تایباد', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تربت جام', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'تربت حیدریه', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'جغتای', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'جوین', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'چناران', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خلیل آباد', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خواف', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خوشاب', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'داورزن', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'درگز', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'رشتخوار', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'زاوه', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'زبرخان', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سبزوار', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سرخس', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'ششتمد', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'صالح آباد', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فریمان', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فیروزه', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'قوچان', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کاشمر', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کلات', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کوهسرخ', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گلبهار', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'گناباد', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'مه ولات', @razavi_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نیشابور', @razavi_khorasan_id);

--
-- South-Khorasan
--
SET @south_khorasan_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@south_khorasan_id, 'خراسان جنوبی', @country_id);
--
-- Cities of South-Khorasan
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بیرجند', @south_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'بشرویه', @south_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'خوسف', @south_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'درمیان', @south_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'زیرکوه', @south_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سرایان', @south_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'سربیشه', @south_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'طبس', @south_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'فردوس', @south_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'قائنات', @south_khorasan_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'نهبندان', @south_khorasan_id);

--
-- Qom
--
SET @qom_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@qom_id, 'قم', @country_id);
--
-- Cities of Qom
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'قم', @qom_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'کهک', @qom_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'جعفرآباد', @qom_id);