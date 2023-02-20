<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="attribute">
        <h2 class="jh-entity-heading" data-cy="attributeDetailsHeading">
          <span v-text="$t('ecommercyApp.attribute.detail.title')">Attribute</span> {{ attribute.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('ecommercyApp.attribute.key')">Key</span>
          </dt>
          <dd>
            <span>{{ attribute.key }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.attribute.value')">Value</span>
          </dt>
          <dd>
            <span>{{ attribute.value }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="attribute.id"
          :to="{ name: 'AttributeEdit', params: { attributeId: attribute.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./attribute-details.component.ts"></script>
