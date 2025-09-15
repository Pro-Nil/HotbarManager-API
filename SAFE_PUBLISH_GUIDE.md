# Safe Publishing Guide

## 🚨 **What Happened Before:**
- The API project got mixed up with the main plugin code
- This caused confusion and accidentally exposed the main plugin source
- Good thing you deleted the repository quickly!

## ✅ **Current Status:**
- ✅ **Main Plugin**: Safe in `../BedWars1058-HotbarManager/`
- ✅ **API Project**: Clean and separate in `./HotbarManager-API/`
- ✅ **No Mixing**: Projects are completely separate

## 🚀 **Safe Publishing Steps:**

### **Step 1: Verify Project Separation**
```bash
# Check main plugin (should NOT have API files)
cd ../BedWars1058-HotbarManager
ls
# Should see: src/, pom.xml, target/, etc. (NO HotbarManager-API folder)

# Check API project (should ONLY have API files)
cd ../HotbarManager-API
ls
# Should see: src/, pom.xml, README.md, LICENSE (NO main plugin files)
```

### **Step 2: Create GitHub Repository for API**
1. Go to https://github.com/new
2. Repository name: `HotbarManager-API`
3. Description: `Official API for HotbarManager plugin - allows developers to create addons and extensions`
4. Make it **Public**
5. **Don't** initialize with README
6. Click "Create repository"

### **Step 3: Initialize Git in API Project**
```bash
# Make sure you're in the API project directory
cd HotbarManager-API

# Initialize git
git init

# Add all files
git add .

# Make initial commit
git commit -m "Initial commit: HotbarManager API v1.5.0"
```

### **Step 4: Connect to GitHub**
```bash
# Add remote (replace YOUR_USERNAME)
git remote add origin https://github.com/YOUR_USERNAME/HotbarManager-API.git

# Push to GitHub
git push -u origin main
```

## 🔒 **Safety Checklist:**

### **Before Publishing:**
- [ ] Main plugin is in separate directory
- [ ] API project only contains API files
- [ ] No main plugin source code in API project
- [ ] Git is initialized in correct directory
- [ ] Repository name is correct

### **After Publishing:**
- [ ] Check GitHub repository contents
- [ ] Verify only API files are visible
- [ ] Test that main plugin is still separate
- [ ] Create first release

## 🎯 **Final Structure:**
```
Your Projects/
├── BedWars1058-HotbarManager/     # Main plugin (separate)
│   ├── src/                       # Plugin source code
│   ├── pom.xml                    # Plugin Maven config
│   └── ...
└── HotbarManager-API/             # API project (separate)
    ├── src/                       # API source code
    ├── pom.xml                    # API Maven config
    ├── README.md                  # API documentation
    └── ...
```

## 🚨 **Important Notes:**
- **Never mix** the two projects
- **Always check** which directory you're in
- **Verify contents** before pushing to GitHub
- **Keep projects separate** at all times

## 🎉 **You're Ready to Publish Safely!**

Follow the steps above and you'll have a clean, professional API repository without any mix-ups!
