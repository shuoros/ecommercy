<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="picture">
        <h2 class="jh-entity-heading" data-cy="pictureDetailsHeading">
          <span v-text="$t('ecommercyApp.picture.detail.title')">Picture</span> {{ picture.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('ecommercyApp.picture.path')">Path</span>
          </dt>
          <dd>
            <span>{{ picture.path }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.picture.product')">Product</span>
          </dt>
          <dd>
            <div v-if="picture.product">
              <router-link :to="{ name: 'ProductView', params: { productId: picture.product.id } }">{{ picture.product.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="picture.id" :to="{ name: 'PictureEdit', params: { pictureId: picture.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./picture-details.component.ts"></script>
