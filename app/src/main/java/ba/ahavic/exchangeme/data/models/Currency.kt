package ba.ahavic.exchangeme.data.models

import ba.ahavic.exchangeme.R

enum class Currency(val currencyName: Int, val icon: Int) {
    AUD(R.string.all_aud, R.drawable.australia),
    BGN(R.string.all_bgn, R.drawable.bulgaria),
    BRL(R.string.all_brl, R.drawable.brazil),
    CAD(R.string.all_cad, R.drawable.canada),
    CHF(R.string.all_chf, R.drawable.switzerland),
    CNY(R.string.all_cny, R.drawable.china),
    CZK(R.string.all_czk, R.drawable.czech_republic),
    DKK(R.string.all_dkk, R.drawable.denmark),
    GBP(R.string.all_gbp, R.drawable.united_kingdom),
    HKD(R.string.all_hkd, R.drawable.hong_kong),
    HRK(R.string.all_hrk, R.drawable.croatia),
    HUF(R.string.all_huf, R.drawable.hungary),
    IDR(R.string.all_idr, R.drawable.indonesia),
    ILS(R.string.all_ils, R.drawable.israel),
    INR(R.string.all_inr, R.drawable.india),
    ISK(R.string.all_isk, R.drawable.iceland),
    KRW(R.string.all_krw, R.drawable.south_korea),
    MXN(R.string.all_mxn, R.drawable.mexico),
    MYR(R.string.all_myr, R.drawable.malaysia),
    NZD(R.string.all_nzd, R.drawable.new_zealand),
    PHP(R.string.all_php, R.drawable.philippines),
    PLN(R.string.all_pln, R.drawable.poland),
    RON(R.string.all_ron, R.drawable.romania),
    RUB(R.string.all_rub, R.drawable.russia),
    SEK(R.string.all_sek, R.drawable.sweden),
    SGD(R.string.all_sgd, R.drawable.singapore),
    THB(R.string.all_thb, R.drawable.thailand),
    TRY(R.string.all_try, R.drawable.turkey),
    USD(R.string.all_usd, R.drawable.united_states_of_america),
    ZAR(R.string.all_zar, R.drawable.south_africa),
    EUR(R.string.all_eur, R.drawable.european_union),
    JPY(R.string.all_jpy, R.drawable.japan),
    NOK(R.string.all_nok, R.drawable.norway)
}
