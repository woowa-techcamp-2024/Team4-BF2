import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
import {VitePWA} from 'vite-plugin-pwa'

export default defineConfig({
  plugins: [
    react(),
    VitePWA({
      registerType: 'autoUpdate',
      includeAssets: ['favicon.ico', 'apple-touch-icon.png', 'masked-icon.svg', 'logo.png'],
      manifest: {
        name: '가게노출에 미쳤습니다 - 노출의민족',
        short_name: '노출의민족',
        description: '배달의민족 서비스 가게노출 서비스 (가짜)팀',
        theme_color: '#ffffff',
        icons: [
          {
            src: '192.png',
            sizes: '192x192',
            type: 'image/png'
          },
          {
            src: '512.png',
            sizes: '512x512',
            type: 'image/png'
          }
        ]
      }
    })
  ],
  server: {
    proxy: {
      '/api/v1/restaurant-summary': {
        target: 'http://localhost:8080/api/v1/restaurant-summary',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/v1\/restaurant-summary/, '')
      },
      '/api/v1/restaurants': {
        target: 'http://localhost:7002/api/v1/restaurants',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/v1\/restaurants/,
          '')
      },
      '/api/v1/menus': {
        target: 'http://localhost:7002/api/v1/menus',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/v1\/menus/, '')
      },
      '/api/v1/ranking': {
        target: 'http://localhost:7001/api/v1/ranking',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/v1\/ranking/, '')
      }
    }
  }
})
