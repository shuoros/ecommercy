<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="comment">
        <h2 class="jh-entity-heading" data-cy="commentDetailsHeading">
          <span v-text="$t('ecommercyApp.comment.detail.title')">Comment</span> {{ comment.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('ecommercyApp.comment.text')">Text</span>
          </dt>
          <dd>
            <span>{{ comment.text }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.comment.product')">Product</span>
          </dt>
          <dd>
            <div v-if="comment.product">
              <router-link :to="{ name: 'ProductView', params: { productId: comment.product.id } }">{{ comment.product.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.comment.customer')">Customer</span>
          </dt>
          <dd>
            <div v-if="comment.customer">
              <router-link :to="{ name: 'CustomerView', params: { customerId: comment.customer.id } }">{{
                comment.customer.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="comment.id" :to="{ name: 'CommentEdit', params: { commentId: comment.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./comment-details.component.ts"></script>
