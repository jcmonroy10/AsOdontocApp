package com.aoo.gestion.navigation

object AooDestinations {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val DASHBOARD = "dashboard"

    const val EVENT_DETAIL = "eventDetail/{eventId}"
    const val PASARELA = "pasarela"
    const val DIPLOMA_PREVIEW = "diplomaPreview/{diplomaId}"
    const val INVOICE = "invoice/{invoiceKey}"
    const val MEMBER = "member/{memberId}"
    const val EDIT_PROFILE = "editProfile"
    const val CHANGE_PASSWORD = "changePassword"
    const val NOTIF_SETTINGS = "notifSettings"
    const val HELP_SUPPORT = "helpSupport"

    fun eventDetail(eventId: Int) = "eventDetail/$eventId"
    fun diplomaPreview(diplomaId: Int) = "diplomaPreview/$diplomaId"
    fun invoice(invoiceKey: String) = "invoice/$invoiceKey"
    fun member(memberId: Int) = "member/$memberId"
}
