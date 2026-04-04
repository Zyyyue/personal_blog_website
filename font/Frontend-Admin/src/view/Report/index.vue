<template>
  <div class="report-page">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stat-cards">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-item">
            <div class="stat-label">总访问量</div>
            <div class="stat-value">{{ statData.totalViews }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-item">
            <div class="stat-label">今日访问</div>
            <div class="stat-value">{{ statData.todayViews }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-item">
            <div class="stat-label">总访客数</div>
            <div class="stat-value">{{ statData.totalVisitors }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-item">
            <div class="stat-label">今日访客</div>
            <div class="stat-value">{{ statData.todayVisitors }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="charts">
      <el-col :span="12">
        <el-card>
          <template #header>访问趋势</template>
          <div ref="viewTrendChartRef" class="chart"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>访客统计</template>
          <div ref="visitorChartRef" class="chart"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="charts">
      <el-col :span="24">
        <el-card>
          <template #header>文章统计</template>
          <div ref="articleChartRef" class="chart"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useReportStore } from '@/stores'
import * as echarts from 'echarts'

const reportStore = useReportStore()

const statData = reactive({
  totalViews: 0,
  todayViews: 0,
  totalVisitors: 0,
  todayVisitors: 0
})

const viewTrendChartRef = ref(null)
const visitorChartRef = ref(null)
const articleChartRef = ref(null)

let viewTrendChart = null
let visitorChart = null
let articleChart = null

const loadStat = async () => {
  const res = await reportStore.fetchStat()
  if (res.data) {
    Object.assign(statData, res.data)
  }
}

const loadViewTrend = async () => {
  const res = await reportStore.fetchViewTrend()
  if (res.data && viewTrendChartRef.value) {
    viewTrendChart = echarts.init(viewTrendChartRef.value)
    viewTrendChart.setOption({
      tooltip: { trigger: 'axis' },
      xAxis: {
        type: 'category',
        data: res.data.dates || []
      },
      yAxis: { type: 'value' },
      series: [
        {
          name: '访问量',
          type: 'line',
          data: res.data.views || [],
          smooth: true,
          itemStyle: { color: '#409EFF' },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: 'rgba(64, 158, 255, 0.5)' },
              { offset: 1, color: 'rgba(64, 158, 255, 0.05)' }
            ])
          }
        }
      ]
    })
  }
}

const loadVisitorSource = async () => {
  const res = await reportStore.fetchVisitorSource()
  if (res.data && visitorChartRef.value) {
    visitorChart = echarts.init(visitorChartRef.value)
    visitorChart.setOption({
      tooltip: { trigger: 'item' },
      series: [
        {
          name: '访客来源',
          type: 'pie',
          radius: ['40%', '70%'],
          data: res.data.sources || [],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    })
  }
}

const loadArticleStat = async () => {
  const res = await reportStore.fetchArticleStat()
  if (res.data && articleChartRef.value) {
    articleChart = echarts.init(articleChartRef.value)
    articleChart.setOption({
      tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
      legend: { data: ['阅读量', '评论量'] },
      xAxis: {
        type: 'category',
        data: res.data.articles?.map(a => a.title) || []
      },
      yAxis: { type: 'value' },
      series: [
        {
          name: '阅读量',
          type: 'bar',
          data: res.data.articles?.map(a => a.viewCount) || [],
          itemStyle: { color: '#409EFF' }
        },
        {
          name: '评论量',
          type: 'bar',
          data: res.data.articles?.map(a => a.commentCount) || [],
          itemStyle: { color: '#67C23A' }
        }
      ]
    })
  }
}

onMounted(() => {
  loadStat()
  loadViewTrend()
  loadVisitorSource()
  loadArticleStat()

  window.addEventListener('resize', () => {
    viewTrendChart?.resize()
    visitorChart?.resize()
    articleChart?.resize()
  })
})
</script>

<style scoped lang="scss">
.report-page {
  padding: 20px;

  .stat-cards {
    margin-bottom: 20px;

    .stat-card {
      .stat-item {
        text-align: center;

        .stat-label {
          font-size: 14px;
          color: #909399;
          margin-bottom: 8px;
        }

        .stat-value {
          font-size: 28px;
          font-weight: 600;
          color: #409EFF;
        }
      }
    }
  }

  .charts {
    margin-bottom: 20px;

    .chart {
      height: 300px;
      width: 100%;
    }
  }
}
</style>
