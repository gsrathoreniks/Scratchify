# Scratchify - A Compose Multiplatform Scratch Card SDK

![Scratchify](image/Scratchify.jpg)
![Github: Open Issues](https://img.shields.io/github/issues-raw/gsrathoreniks/scratchify?color=7E8EFB&label=Scratchify%3A%20Open%20Issues)
![Scratchify](https://img.shields.io/maven-central/v/io.github.gsrathoreniks/scratchify?style=flat&label=Scratchify)
[![Github Followers](https://img.shields.io/github/followers/gsrathoreniks?label=Follow&style=social)](https://github.com/gsrathoreniks)
[![Twitter Follow](https://img.shields.io/twitter/follow/gsrathoreniks?label=Follow&style=social)](https://twitter.com/gsrathoreniks)
![GitHub License](https://img.shields.io/github/license/gsrathoreniks/scratchify?link=https%3A%2F%2Fgithub.com%2Fgsrathoreniks%2FScratchify%3Ftab%3DMIT-1-ov-file%23MIT-1-ov-file)

---

## 🚀 Introduction

Scratchify is a lightweight and customizable scratch card SDK built using Jetpack Compose Multiplatform. It enables you to create interactive scratch surfaces where users can scratch off an overlay to reveal hidden content underneath. Ideal for rewards, discounts, surprise reveals, and gamification elements in your app!

Perfect for:  
✅ **Reward reveals**  
✅ **Discount coupons**  
✅ **Surprise elements**  
✅ **Gamification features** 

---

## ✨ Features

✔️ **Multiplatform support** (Android & iOS)  
✔️ **Two-layer scratch surface** (Overlay & Revealed Content)  
✔️ **Customizable brush size**  
✔️ **Auto-reveal after X% scratched**  
✔️ **Instant reveal & reset options**  
✔️ **Scratch event callbacks** (`onScratchStarted`, `onScratchProgress`, `onScratchCompleted`)  


---

## 📦 Implementation

### 1️⃣ Add Dependency


Since **Scratchify** is a Compose Multiplatform (CMP) library, you should add it to your **`commonMain`** source set to use it across both iOS and Android.

Add the dependency in your `shared` module's `build.gradle.kts`:


[![Maven Central](https://img.shields.io/maven-central/v/io.github.gsrathoreniks/scratchify?style=flat)](https://central.sonatype.com/search?q=io.github.gsrathoreniks.scratchify)  
```kotlin
dependencies {
    implementation("io.github.gsrathoreniks:scratchify:<latest_version>")
}
```

---


## 🤝 Contributing  

We welcome contributions from the community! 🚀  

### 🛠 Submitting Issues & Feature Requests  
- Found a **bug**? **[Open an Issue](https://github.com/gsrathoreniks/scratchify/issues/new?labels=bug&template=bug_report.md)**  
- Have a **feature request**? **[Suggest a Feature](https://github.com/gsrathoreniks/scratchify/issues/new?labels=enhancement&template=feature_request.md)**  

We appreciate your help in improving **Scratchify**! 🎉  

---

## 📜 License  

This project is licensed under the **MIT License**.  

📄 **Read the full license**: [MIT License](https%3A%2F%2Fgithub.com%2Fgsrathoreniks%2FScratchify%3Ftab%3DMIT-1-ov-file%23MIT-1-ov-file)  




