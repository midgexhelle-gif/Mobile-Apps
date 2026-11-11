package com.example.ads

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class RewardType(val title: String, val rewardCoins: Int, val description: String) {
  FREE_COINS("50 Brain Coins", 50, "Claim 50 free coins to spend on hints and items"),
  EXTRA_HINT("Free Tier-1 Hint", 15, "Instantly unlock a hint on your active puzzle"),
  TIME_EXTENSION("+15 Seconds", 20, "Add 15 precious seconds to your countdown timer"),
  SECOND_CHANCE("Extra Life", 25, "Continue your level without resetting your streak")
}

data class ActiveAdState(
  val isShowing: Boolean = false,
  val isRewarded: Boolean = false,
  val rewardType: RewardType = RewardType.FREE_COINS,
  val adTitle: String = "Google AdMob Test Partner",
  val timeRemainingSeconds: Int = 5,
  val canSkip: Boolean = false
)

object AdManager {

  // Official Google Mobile Ads Android Test Unit IDs
  const val TEST_APP_ID = "ca-app-pub-3940256099942544~3347511713"
  const val TEST_BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"
  const val TEST_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
  const val TEST_REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"

  private val _activeAd = MutableStateFlow(ActiveAdState())
  val activeAd: StateFlow<ActiveAdState> = _activeAd.asStateFlow()

  private var onRewardEarnedCallback: ((RewardType) -> Unit)? = null
  private var onAdClosedCallback: (() -> Unit)? = null

  fun showRewardedAd(
    rewardType: RewardType,
    onRewardEarned: (RewardType) -> Unit,
    onClosed: () -> Unit = {}
  ) {
    onRewardEarnedCallback = onRewardEarned
    onAdClosedCallback = onClosed
    _activeAd.value = ActiveAdState(
      isShowing = true,
      isRewarded = true,
      rewardType = rewardType,
      adTitle = "Test Rewarded Ad (AdMob 5224354917)",
      timeRemainingSeconds = 5,
      canSkip = false
    )
  }

  fun showInterstitialAd(onClosed: () -> Unit = {}) {
    onAdClosedCallback = onClosed
    _activeAd.value = ActiveAdState(
      isShowing = true,
      isRewarded = false,
      rewardType = RewardType.FREE_COINS,
      adTitle = "Test Interstitial Ad (AdMob 1033173712)",
      timeRemainingSeconds = 3,
      canSkip = true
    )
  }

  fun completeReward() {
    val type = _activeAd.value.rewardType
    _activeAd.value = ActiveAdState(isShowing = false)
    onRewardEarnedCallback?.invoke(type)
    onRewardEarnedCallback = null
    onAdClosedCallback?.invoke()
    onAdClosedCallback = null
  }

  fun dismissAd() {
    _activeAd.value = ActiveAdState(isShowing = false)
    onAdClosedCallback?.invoke()
    onRewardEarnedCallback = null
    onAdClosedCallback = null
  }
}
