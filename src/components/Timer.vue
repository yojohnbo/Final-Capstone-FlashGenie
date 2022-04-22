<template>
  <div class="container">
    {{ countDown.toFixed(2) }}
  </div>
</template>

<script>
export default {
  name: "Timer",
  props: ["lastCard"],
  data() {
    return {
      countDown: 10.0,
    };
  },
  computed: {
    isTimerEnabled() {
      return this.$store.state.timerEnabled;
    },
  },
  methods: {
    countDownTimer() {
      if (this.isTimerEnabled && this.countDown >= 0) {
        setTimeout(() => {
          this.countDown -= 0.01;
          if (this.countDown <= 0) {
            this.passEvent();
            if (this.lastCard) {
              this.endSession();
            }
            setTimeout(() => {
              this.countDown = 10;
            }, 10);
          }
          this.countDownTimer();
        }, 10);
      }
    },
    resetTimer() {
      this.countDown = 10;
    },
    passEvent() {
      this.$emit("timerZero");
    },
    endSession() {
      this.$emit("endLightningRound");
    },
  },
  created() {
    this.countDownTimer();
  },
};
</script>

<style>
</style>