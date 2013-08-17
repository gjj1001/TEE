﻿package com.alipay.android.appDemo4;

public class PartnerConfig {

	// 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
	public static final String PARTNER = "2088011650002074";
	// 商户收款的支付宝账号
	public static final String SELLER = "2088011650002074";
	// 商户（RSA）私钥
	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANRXy7/ykPhagSlM2NvYpXF/uDZCH8jj1QaUbRfqtf9RGPhmH93hIAdYnqoeXrng2DFoCK9PZBQ6BIM5//lz6JETvE2QqRxZteePNI8dBl49O4mVviiLTEpqVsCXVPwCuy6qZDxt7HPeFcrTU4EeWv91kmnx4w2AGrgREykUC88fAgMBAAECgYEArspyete2Q+lkJtYOMLIeRdZnkapOtbPteBQX1TcshzV/g4G1O5SEI+fr+4cyXHncHJdRhtWoQQodCtRkhDfIOJtzpGK5mKMb7E7qfJ9QWrhRS5etO0Wuxf8IVw7OceYKbLRrPSzK8StNo7B+q9Ph22A36W1Gl0SpWBMIHhuuDmECQQD3EfkKDTW0Q4a2PB6PsetL+bAjlLqI+U/ZEAKJhiNz27lebF5Z4rYdb2e/ZHjMTKwd4QEjBwCP6ma6NixFN7UPAkEA3ASB/ChHEcWnQEO31Wmfb9OP4M+lo8k9iRRWGvSBvlR/+YH5iPWY4S9wXA6sXJBTE5JKb8VtOJ6dxFREZxfk8QJBAK/3KPuztNASV9tqrgmBmieeSYjN4Jy1k9zwvh3cX1ug3kEVxY0XHsWQFsAFetNmuB7/paOg1RGqzEdLVfRmXJkCQECWli4XCN3/vxyPhJ0C18o1OiyN6JtPTWA7dxeMpbXhCnyNmlqJdqrMiViM+i5mM+0v6egr9edEMR5ceoQlMJECQQCl1QNMjvVILLeqbUyTAUeCmir59NFMlfhmYHhoEY5iW3Wc9vr0igsEnWXmVvpPC4MPU7uVB610aZGxKrKy2Vrs";
	// 支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
	public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDUV8u/8pD4WoEpTNjb2KVxf7g2Qh/I49UGlG0X6rX/URj4Zh/d4SAHWJ6qHl654NgxaAivT2QUOgSDOf/5c+iRE7xNkKkcWbXnjzSPHQZePTuJlb4oi0xKalbAl1T8ArsuqmQ8bexz3hXK01OBHlr/dZJp8eMNgBq4ERMpFAvPHwIDAQAB";
	// 支付宝安全支付服务apk的名称，必须与assets目录下的apk名称一致
	public static final String ALIPAY_PLUGIN_NAME = "alipay_plugin_20120428msp.apk";

}
