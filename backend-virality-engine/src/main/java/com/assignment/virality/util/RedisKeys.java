package com.assignment.virality.util;

public final class RedisKeys {

    private RedisKeys() {
        // prevent instantiation
    }

    // 🔥 Virality Score
    public static String viralityScore(Long postId) {
        return "post:" + postId + ":virality_score";
    }

    // 🤖 Bot Count (Horizontal Cap)
    public static String botCount(Long postId) {
        return "post:" + postId + ":bot_count";
    }

    // ⏳ Cooldown (Bot → Human)
    public static String cooldown(Long botId, Long humanId) {
        return "cooldown:bot_" + botId + ":human_" + humanId;
    }

    // 🔔 Notification Cooldown (15 min)
    public static String notificationCooldown(Long userId) {
        return "notif:cooldown:" + userId;
    }

    // 📩 Pending Notifications List
    public static String pendingNotifications(Long userId) {
        return "user:" + userId + ":pending_notifs";
    }

    // 🔍 Pattern for Scheduler Scan
    public static String pendingNotificationsPattern() {
        return "user:*:pending_notifs";
    }
}