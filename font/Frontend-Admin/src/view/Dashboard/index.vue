<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <!-- 统计卡片 -->
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon article">
              <el-icon><Document /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ overview.articleCount || 0 }}</div>
              <div class="stat-label">文章总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon view">
              <el-icon><View /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ overview.viewCount || 0 }}</div>
              <div class="stat-label">浏览总量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon visitor">
              <el-icon><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ overview.visitorCount || 0 }}</div>
              <div class="stat-label">访客总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon comment">
              <el-icon><ChatDotRound /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ overview.commentCount || 0 }}</div>
              <div class="stat-label">评论总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>访客趋势</span>
            </div>
          </template>
          <div ref="visitorChartRef" class="chart" />
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>文章 Top10</span>
            </div>
          </template>
          <div ref="articleTopChartRef" class="chart" />
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="24">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>访客省份分布</span>
            </div>
          </template>
          <div ref="mapChartRef" class="map-chart" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Document, View, User, ChatDotRound } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import { useReportStore } from '@/stores'

const reportStore = useReportStore()

const overview = ref({})
const visitorChartRef = ref(null)
const articleTopChartRef = ref(null)
const mapChartRef = ref(null)

const loadOverview = async () => {
  await reportStore.fetchOverview()
  overview.value = reportStore.overview || {}
}

const initVisitorChart = () => {
  if (!visitorChartRef.value) return
  const chart = echarts.init(visitorChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    },
    yAxis: { type: 'value' },
    series: [
      {
        name: '访客数',
        type: 'line',
        data: [120, 200, 150, 80, 70, 110, 130],
        smooth: true,
        itemStyle: { color: '#409EFF' }
      }
    ]
  })
}

const initArticleTopChart = () => {
  if (!articleTopChartRef.value) return
  const chart = echarts.init(articleTopChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'value' },
    yAxis: { type: 'category', data: [] },
    series: [
      {
        name: '浏览量',
        type: 'bar',
        data: [],
        itemStyle: { color: '#67C23A' }
      }
    ]
  })
}

const initMapChart = () => {
  if (!mapChartRef.value) return
  const chart = echarts.init(mapChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'item' },
    visualMap: {
      min: 0,
      max: 1000,
      left: 'left',
      top: 'bottom',
      text: ['多', '少'],
      calculable: true
    },
    series: [
      {
        name: '访客数',
        type: 'map',
        mapType: 'china',
        roam: false,
        data: []
      }
    ]
  })
}

onMounted(() => {
  loadOverview()
  setTimeout(() => {
    initVisitorChart()
    initArticleTopChart()
    initMapChart()
  }, 100)

  window.addEventListener('resize', () => {
    echarts.getInstanceByDom(visitorChartRef.value)?.resize()
    echarts.getInstanceByDom(articleTopChartRef.value)?.resize()
    echarts.getInstanceByDom(mapChartRef.value)?.resize()
  })
})
</script>

<style scoped lang="scss">
.dashboard-container {
  padding: 20px;
}

.stat-card {
  .stat-content {
    display: flex;
    align-items: center;
    gap: 16px;
  }

  .stat-icon {
    width: 64px;
    height: 64px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 28px;
    color: #fff;

    &.article {
      background: linear-gradient(135deg, #667eea, #764ba2);
    }

    &.view {
      background: linear-gradient(135deg, #f093fb, #f5576c);
    }

    &.visitor {
      background: linear-gradient(135deg, #4facfe, #00f2fe);
    }

    &.comment {
      background: linear-gradient(135deg, #43e97b, #38f9d7);
    }
  }

  .stat-info {
    flex: 1;

    .stat-value {
      font-size: 28px;
      font-weight: 600;
      color: #303133;
    }

    .stat-label {
      font-size: 14px;
      color: #909399;
      margin-top: 4px;
    }
  }
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
}

.chart {
  height: 300px;
}

.map-chart {
  height: 400px;
}
</style>
