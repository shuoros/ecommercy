<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="category">
        <h2 class="jh-entity-heading" data-cy="categoryDetailsHeading">
          <span v-text="$t('ecommercyApp.category.detail.title')">Category</span> {{ category.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('ecommercyApp.category.name')">Name</span>
          </dt>
          <dd>
            <span>{{ category.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.category.product')">Product</span>
          </dt>
          <dd>
            <span v-for="(product, i) in category.products" :key="product.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'ProductView', params: { productId: product.id } }">{{ product.id }}</router-link>
            </span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.category.group')">Group</span>
          </dt>
          <dd>
            <div v-if="category.group">
              <router-link :to="{ name: 'GroupView', params: { groupId: category.group.id } }">{{ category.group.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="category.id" :to="{ name: 'CategoryEdit', params: { categoryId: category.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./category-details.component.ts"></script>
