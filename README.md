## 🌱 Planter
반려식물 돌봄 매칭, 커뮤니티 서비스 Planter

### Tech stack
- Spring boot
- Kotlin
- Gradle
- JPA, Hibernate
- Spring Security


### API
- `production`: https://planter22.shop
- `dev`: https://dev.planter22.shop

[API 명세서](https://subari.notion.site/API-2105e18622124ca5a2a428c8d008cf0e)

### Architecture

### Directory
```
├──📄README.md
├──📂build
├──📄build.gradle.kts
├──📂gradle
├──📄settings.gradle.kts
└── 📂src
    ├──📂main
    │   ├──📂kotlin
    │   │   └── 📂com
    │   │       └── 📂twotwo
    │   │           └── 📂planter
    │   │               ├──📄PlanterApplication.kt
    │   │               ├──📂auth
    │   │               │   ├──📂controller
    │   │               │   │   └── 📄AuthController.kt
    │   │               │   ├──📂dto
    │   │               │   │   ├──📄CheckDuplicateReq.kt
    │   │               │   │   ├──📄Message.kt
    │   │               │   │   ├──📄SendCodeReq.kt
    │   │               │   │   ├──📄SendSmsReq.kt
    │   │               │   │   ├──📄SendSmsRes.kt
    │   │               │   │   ├──📄UserLoginReq.kt
    │   │               │   │   ├──📄UserLoginRes.kt
    │   │               │   │   ├──📄UserRegisterReq.kt
    │   │               │   │   ├──📄UserRegisterRes.kt
    │   │               │   │   └── 📄VerifyCodeReq.kt
    │   │               │   ├──📂exception
    │   │               │   │   └── 📄AuthenticationException.kt
    │   │               │   └── 📂service
    │   │               │       ├──📄AuthService.kt
    │   │               │       ├──📄CertificateCodeService.kt
    │   │               │       └── 📄SmsService.kt
    │   │               ├──📂common
    │   │               │   ├──📂domain
    │   │               │   ├──📂dto
    │   │               │   │   └── 📄ErrorResponse.kt
    │   │               │   └── 📂service
    │   │               │       └── 📄AwsS3Service.kt
    │   │               ├──📂config
    │   │               │   ├──📄AwsConfig.kt
    │   │               │   ├──📄AwsProperties.kt
    │   │               │   ├──📄CustomAuthenticationEntryPoint.kt
    │   │               │   ├──📄GlobalExceptionHandler.kt
    │   │               │   ├──📄RedisConfig.kt
    │   │               │   └── 📄WebServerConfig.kt
    │   │               ├──📂manager
    │   │               │   ├──📄controller
    │   │               │   │   └── 📄PlantManagerController.kt
    │   │               │   ├──📂domain
    │   │               │   │   ├──📄ManagerImg.kt
    │   │               │   │   ├──📄ManagerTag.kt
    │   │               │   │   ├──📄PlantCareOption.kt
    │   │               │   │   ├──📄PlantManager.kt
    │   │               │   │   ├──📄PlantManagerCategory.kt
    │   │               │   │   └── 📄PlantManagerStatus.kt
    │   │               │   ├──📂dto
    │   │               │   │   ├──📄CreatePlantManagerReq.kt
    │   │               │   │   ├──📄GetPlantManagerListRes.kt
    │   │               │   │   ├──📄GetPlantManagerOptionRes.kt
    │   │               │   │   └── 📄GetPlantManagerRes.kt
    │   │               │   ├──📂repository
    │   │               │   │   ├──📄PlantCareOptionRepository.kt
    │   │               │   │   └── 📄PlantManagerRepository.kt
    │   │               │   ├──📂service
    │   │               │   │   └── 📄PlantManagerService.kt
    │   │               │   └── 📂util
    │   │               │       └── 📄PlantManagerUtil.kt
    │   │               ├──📂matching
    │   │               │   ├──📂controller
    │   │               │   │   └── 📄MatchingController.kt
    │   │               │   ├──📂domain
    │   │               │   │   ├──📄Matching.kt
    │   │               │   │   ├──📄MatchingStatus.kt
    │   │               │   │   ├──📄PickUpType.kt
    │   │               │   │   ├──📄PlantService.kt
    │   │               │   │   └── 📄PlantServiceOption.kt
    │   │               │   ├──📂dto
    │   │               │   │   ├──📄CreateMatchingReq.kt
    │   │               │   │   ├──📄CreateMatchingRes.kt
    │   │               │   │   ├──📄GetMatchingDetailRes.kt
    │   │               │   │   ├──📄GetMatchingListRes.kt
    │   │               │   │   ├──📄ModifyMatchingStatusReq.kt
    │   │               │   │   ├──📄ModifyMatchingStatusRes.kt
    │   │               │   │   ├──📄PlantServiceRes.kt
    │   │               │   │   └── 📄PlantToCare.kt
    │   │               │   ├──📂repository
    │   │               │   │   ├──📄MatchingRepository.kt
    │   │               │   │   ├──📄PlantServiceOptionRepository.kt
    │   │               │   │   └── 📄PlantServiceRepository.kt
    │   │               │   └── 📂service
    │   │               │       └── 📄MatchingService.kt
    │   │               ├──📂message
    │   │               │   ├──📂controller
    │   │               │   │   └── 📄MessageController.kt
    │   │               │   ├──📂domain
    │   │               │   │   ├──📄Message.kt
    │   │               │   │   ├──📄MessageImg.kt
    │   │               │   │   ├──📄MessageStatus.kt
    │   │               │   │   └── 📄SenderType.kt
    │   │               │   ├──📂dto
    │   │               │   │   ├──📄GetMessageDetailRes.kt
    │   │               │   │   ├──📄GetMessageGroupRes.kt
    │   │               │   │   ├──📄SendMessageReq.kt
    │   │               │   │   └── 📄SendMessageRes.kt
    │   │               │   ├──📂repository
    │   │               │   │   ├──📄MessageImgRepository.kt
    │   │               │   │   └── 📄MessageRepository.kt
    │   │               │   └── 📂service
    │   │               │       ├──📄MessageImgService.kt
    │   │               │       └── 📄MessageService.kt
    │   │               ├──📂report
    │   │               │   ├──📂controller
    │   │               │   │   └── 📄ReportController.kt
    │   │               │   ├──📂domain
    │   │               │   │   └── 📄Report.kt
    │   │               │   ├──📂dto
    │   │               │   │   ├──📄CreateReportReq.kt
    │   │               │   │   └── 📄CreateReportRes.kt
    │   │               │   ├──📂repository
    │   │               │   │   └── 📄ReportRepository.kt
    │   │               │   └── 📂service
    │   │               │       └── 📄ReportService.kt
    │   │               ├──📂review
    │   │               │   ├──📄controller
    │   │               │   │   └── 📄ReviewController.kt
    │   │               │   ├──📂domain
    │   │               │   │   ├──📄Review.kt
    │   │               │   │   └── 📄ReviewImg.kt
    │   │               │   ├──📂dto
    │   │               │   │   ├──📄GetReviewListRes.kt
    │   │               │   │   ├──📄WriteReviewReq.kt
    │   │               │   │   └── 📄WriteReviewRes.kt
    │   │               │   ├──📂repository
    │   │               │   │   ├──📄ReviewImgRepository.kt
    │   │               │   │   └── 📄ReviewRepository.kt
    │   │               │   └── 📂service
    │   │               │       ├──📄ReviewImgService.kt
    │   │               │       └── 📄ReviewService.kt
    │   │               ├──📂security
    │   │               │   ├──📄JwtAuthenticationFilter.kt
    │   │               │   ├──📄JwtProperties.kt
    │   │               │   ├──📄JwtTokenProvider.kt
    │   │               │   └── 📄SecurityConfig.kt
    │   │               ├──📂user
    │   │               │   ├──📂controller
    │   │               │   │   └── 📄UserController.kt
    │   │               │   ├──📂domain
    │   │               │   │   ├──📄User.kt
    │   │               │   │   └── 📄UserStatus.kt
    │   │               │   ├──📂dto
    │   │               │   │   ├──📄GetMyPageRes.kt
    │   │               │   │   └── 📄WithdrawUserRes.kt
    │   │               │   ├──📂repository
    │   │               │   │   └── 📄UserRepository.kt
    │   │               │   └── 📂service
    │   │               │       ├──📄UserDetailService.kt
    │   │               │       └── 📄UserService.kt
    │   │               └── 📂util
    │   │                   ├──📄 BaseException.kt
    │   │                   ├──📄BaseResponse.kt
    │   │                   ├──📄BaseResponseCode.kt
    │   │                   ├──📄BaseTime.kt
    │   │                   ├──📄CommonUtil.kt
    │   │                   └── 📄ErrorResponse.kt
    │   └── 📂resources
    │       ├──📂META-INF
    │       ├──📄application.yml
    │       └── 📄log4j2.xml
    └── 📂test
        └── 📂kotlin
            └── 📂com
                └── 📂twotwo
                    └── 📂planter
                        └── 📄PlanterApplicationTests.kt
```

